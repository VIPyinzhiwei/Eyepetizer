/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepetizer.android.ui.newdetail

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.*
import com.eyepetizer.android.logic.model.Author
import com.eyepetizer.android.logic.model.Consumption
import com.eyepetizer.android.logic.model.Cover
import com.eyepetizer.android.logic.model.WebUrl
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.ui.common.view.NoStatusFooter
import com.eyepetizer.android.ui.login.LoginActivity
import com.eyepetizer.android.util.*
import com.shuyu.gsyvideoplayer.GSYVideoADManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_new_detail.*
import kotlinx.coroutines.*


/**
 * 视频详情界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/14
 */
class NewDetailActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this, InjectorUtil.getNewDetailViewModelFactory()).get(NewDetailViewModel::class.java) }

    private lateinit var relatedAdapter: NewDetailRelatedAdapter

    private lateinit var replyAdapter: NewDetailReplyAdapter

    private lateinit var mergeAdapter: MergeAdapter

    private var orientationUtils: OrientationUtils? = null

    private val globalJob by lazy { Job() }

    private var hideTitleBarJob: Job? = null

    private var hideBottomContainerJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_detail)
    }

    override fun setContentView(layoutResID: Int) {
        if (checkArguments()) {
            super.setContentView(layoutResID)
            setStatusBarBackground(R.color.black)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (checkArguments()) {
            initParams()
            startVideoPlayer()
            viewModel.onRefresh()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoADManager.releaseAllVideos()
        orientationUtils?.releaseListener()
        videoPlayer.release()
        videoPlayer.setVideoAllCallBack(null)
        globalJob.cancel()
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    override fun setupViews() {
        super.setupViews()
        initParams()
        orientationUtils = OrientationUtils(this, videoPlayer)
        relatedAdapter = NewDetailRelatedAdapter(this, viewModel.relatedDataList, viewModel.videoInfoData)
        replyAdapter = NewDetailReplyAdapter(this, viewModel.repliesDataList)
        mergeAdapter = MergeAdapter(relatedAdapter, replyAdapter)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mergeAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        refreshLayout.run {
            setDragRate(0.7f)
            setHeaderTriggerRate(0.6f)
            setFooterTriggerRate(0.6f)
            setEnableLoadMoreWhenContentNotFull(true)
            setEnableFooterFollowWhenNoMoreData(true)
            setEnableFooterTranslationContent(true)
            setEnableScrollContentWhenLoaded(true)
            refreshLayout.setEnableNestedScroll(true)
            setFooterHeight(153f)
            setRefreshFooter(NoStatusFooter(this@NewDetailActivity).apply {
                setAccentColorId(R.color.white)
                setTextTitleSize(16f)
            })
            setOnRefreshListener { finish() }
            setOnLoadMoreListener { viewModel.onLoadMore() }
        }
        setOnClickListener(
            ivPullDown, ivMore, ivShare, ivCollection, ivToWechatFriends, ivShareToWechatMemories,
            ivShareToWeibo, ivShareToQQ, ivShareToQQzone, ivAvatar, etComment, ivReply, tvReplyCount, listener = ClickListener()
        )
        observe()
        startVideoPlayer()
        viewModel.onRefresh()
    }

    private fun checkArguments() = if (intent.getParcelableExtra<VideoInfo>(EXTRA_VIDEOINFO) == null && intent.getLongExtra(EXTRA_VIDEO_ID, 0L) == 0L) {
        GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
        finish()
        false
    } else {
        true
    }

    private fun initParams() {
        if (intent.getParcelableExtra<VideoInfo>(EXTRA_VIDEOINFO) != null) viewModel.videoInfoData = intent.getParcelableExtra(EXTRA_VIDEOINFO)
        if (intent.getLongExtra(EXTRA_VIDEO_ID, 0L) != 0L) viewModel.videoId = intent.getLongExtra(EXTRA_VIDEO_ID, 0L)
    }

    private fun startVideoPlayer() {
        viewModel.videoInfoData?.run {
            ivBlurredBg.load(cover.blurred)
            tvReplyCount.text = consumption.replyCount.toString()
            videoPlayer.startPlay()
        }
    }

    private fun observe() {
        //刷新，视频信息+相关推荐+评论
        if (!viewModel.videoDetailLiveData.hasObservers()) {
            viewModel.videoDetailLiveData.observe(this, Observer { result ->
                val response = result.getOrNull()
                if (response == null) {
                    ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
                    return@Observer
                }
                viewModel.nextPageUrl = response.videoReplies.nextPageUrl
                if (response.videoRelated == null || response.videoRelated.itemList.isNullOrEmpty() && response.videoReplies.itemList.isNullOrEmpty()) {
                    return@Observer
                }
                response.videoBeanForClient?.run {
                    viewModel.videoInfoData = VideoInfo(id, playUrl, title, description, category, library, consumption, cover, author, webUrl)
                    startVideoPlayer()
                    relatedAdapter.bindVideoInfo(viewModel.videoInfoData)
                }
                viewModel.relatedDataList.clear()
                viewModel.repliesDataList.clear()
                viewModel.relatedDataList.addAll(response.videoRelated.itemList)
                viewModel.repliesDataList.addAll(response.videoReplies.itemList)
                relatedAdapter.notifyDataSetChanged()
                replyAdapter.notifyDataSetChanged()
                when {
                    viewModel.repliesDataList.isNullOrEmpty() -> refreshLayout.finishLoadMoreWithNoMoreData()
                    response.videoReplies.nextPageUrl.isNullOrEmpty() -> refreshLayout.finishLoadMoreWithNoMoreData()
                    else -> refreshLayout.closeHeaderOrFooter()
                }
            })
        }
        //刷新，相关推荐+评论
        if (!viewModel.repliesAndRepliesLiveData.hasObservers()) {
            viewModel.repliesAndRepliesLiveData.observe(this, Observer { result ->
                val response = result.getOrNull()
                if (response == null) {
                    ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
                    return@Observer
                }
                viewModel.nextPageUrl = response.videoReplies.nextPageUrl
                if (response.videoRelated == null || response.videoRelated.itemList.isNullOrEmpty() && response.videoReplies.itemList.isNullOrEmpty()) {
                    return@Observer
                }
                viewModel.relatedDataList.clear()
                viewModel.repliesDataList.clear()
                viewModel.relatedDataList.addAll(response.videoRelated.itemList)
                viewModel.repliesDataList.addAll(response.videoReplies.itemList)
                relatedAdapter.bindVideoInfo(viewModel.videoInfoData)
                relatedAdapter.notifyDataSetChanged()
                replyAdapter.notifyDataSetChanged()
                when {
                    viewModel.repliesDataList.isNullOrEmpty() -> refreshLayout.finishLoadMoreWithNoMoreData()
                    response.videoReplies.nextPageUrl.isNullOrEmpty() -> refreshLayout.finishLoadMoreWithNoMoreData()
                    else -> refreshLayout.closeHeaderOrFooter()
                }
            })
        }
        //上拉加载，评论
        if (!viewModel.repliesLiveData.hasObservers()) {
            viewModel.repliesLiveData.observe(this, Observer { result ->
                val response = result.getOrNull()
                if (response == null) {
                    ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
                    return@Observer
                }
                viewModel.nextPageUrl = response.nextPageUrl
                if (response.itemList.isNullOrEmpty()) {
                    return@Observer
                }
                val itemCount = replyAdapter.itemCount
                viewModel.repliesDataList.addAll(response.itemList)
                replyAdapter.notifyItemRangeInserted(itemCount, response.itemList.size)
                if (response.nextPageUrl.isNullOrEmpty()) {
                    refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    refreshLayout.closeHeaderOrFooter()
                }
            })
        }
    }

    private fun GSYVideoPlayer.startPlay() {
        viewModel.videoInfoData?.let {
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            fullscreenButton.setOnClickListener { showFull() }
            //防止错位设置
            playTag = TAG
            //音频焦点冲突时是否释放
            isReleaseWhenLossAudio = false
            //增加封面
            val imageView = ImageView(this@NewDetailActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(it.cover.detail)
            thumbImageView = imageView
            thumbImageView.setOnClickListener { switchTitleBarVisible() }
            //是否开启自动旋转
            isRotateViewAuto = false
            //是否需要全屏锁定屏幕功能
            isNeedLockFull = true
            //是否可以滑动调整
            setIsTouchWiget(true)
            //设置触摸显示控制ui的消失时间
            dismissControlTime = 5000
            //设置播放过程中的回调
            setVideoAllCallBack(VideoCallPlayBack())
            //设置播放URL
            setUp(it.playUrl, false, it.title)
            //开始播放
            startPlayLogic()
        }
    }

    private fun switchTitleBarVisible() {
        if (videoPlayer.currentPlayer.currentState == GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) return
        if (flHeader.visibility == View.VISIBLE) {
            hideTitleBar()
        } else {
            flHeader.visibleAlphaAnimation(1000)
            ivPullDown.visibleAlphaAnimation(1000)
            ivCollection.visibleAlphaAnimation(1000)
            ivMore.visibleAlphaAnimation(1000)
            ivShare.visibleAlphaAnimation(1000)
            delayHideTitleBar()
        }
    }

    private fun hideTitleBar() {
        flHeader.invisibleAlphaAnimation(1000)
        ivPullDown.goneAlphaAnimation(1000)
        ivCollection.goneAlphaAnimation(1000)
        ivMore.goneAlphaAnimation(1000)
        ivShare.goneAlphaAnimation(1000)
    }

    private fun delayHideTitleBar() {
        hideTitleBarJob?.cancel()
        hideTitleBarJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(videoPlayer.dismissControlTime.toLong())
            hideTitleBar()
        }
    }

    private fun delayHideBottomContainer() {
        hideBottomContainerJob?.cancel()
        hideBottomContainerJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(videoPlayer.dismissControlTime.toLong())
            videoPlayer.getBottomContainer().gone()
            videoPlayer.startButton.gone()
        }
    }

    private fun showFull() {
        orientationUtils?.run { if (isLand != 1) resolveByClick() }
        videoPlayer.startWindowFullscreen(this, true, false)
    }

    fun scrollTop() {
        if (relatedAdapter.itemCount != 0) {
            recyclerView.scrollToPosition(0)
            refreshLayout.invisibleAlphaAnimation(2500)
            refreshLayout.visibleAlphaAnimation(1500)
        }
    }

    private fun scrollRepliesTop() {
        val targetPostion = (relatedAdapter.itemCount - 1) + 2  //+相关推荐最后一项，+1评论标题，+1条评论
        if (targetPostion < mergeAdapter.itemCount - 1) {
            recyclerView.smoothScrollToPosition(targetPostion)
        }
    }

    inner class VideoCallPlayBack : GSYSampleCallBack() {
        override fun onStartPrepared(url: String?, vararg objects: Any?) {
            super.onStartPrepared(url, *objects)
            flHeader.gone()
            llShares.gone()
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
            super.onClickBlank(url, *objects)
            switchTitleBarVisible()
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
            super.onClickStop(url, *objects)
            delayHideBottomContainer()
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
            super.onAutoComplete(url, *objects)
            flHeader.visible()
            ivPullDown.visible()
            ivCollection.gone()
            ivShare.gone()
            ivMore.gone()
            llShares.visible()
        }
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            viewModel.videoInfoData?.let {
                when (v) {
                    ivPullDown -> finish()
                    ivMore -> {
                    }
                    ivShare -> showDialogShare(it.webUrl.raw)
                    ivCollection -> LoginActivity.start(this@NewDetailActivity)
                    ivToWechatFriends -> share(it.webUrl.raw, SHARE_WECHAT)
                    ivShareToWechatMemories -> share(it.webUrl.raw, SHARE_WECHAT_MEMORIES)
                    ivShareToWeibo -> share(it.webUrl.forWeibo, SHARE_WEIBO)
                    ivShareToQQ -> share(it.webUrl.raw, SHARE_QQ)
                    ivShareToQQzone -> share(it.webUrl.raw, SHARE_QQZONE)
                    ivAvatar, etComment -> LoginActivity.start(this@NewDetailActivity)
                    ivReply, tvReplyCount -> scrollRepliesTop()
                    else -> {
                    }
                }
            }
        }
    }

    @Parcelize
    data class VideoInfo(
        val videoId: Long,
        val playUrl: String,
        val title: String,
        val description: String,
        val category: String,
        val library: String,
        val consumption: Consumption,
        val cover: Cover,
        val author: Author?,
        val webUrl: WebUrl
    ) : Parcelable

    companion object {
        const val TAG = "NewDetailActivity"

        const val EXTRA_VIDEOINFO = "videoInfo"
        const val EXTRA_VIDEO_ID = "videoId"

        fun start(context: Activity, videoInfo: VideoInfo) {
            val starter = Intent(context, NewDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEOINFO, videoInfo)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }

        fun start(context: Activity, videoId: Long) {
            val starter = Intent(context, NewDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_ID, videoId)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }
}