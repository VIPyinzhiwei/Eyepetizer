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
package com.eyepetizer.android.ui.ugcdetail

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.gone
import com.eyepetizer.android.extension.visible
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * 社区-推荐详情页面对应的视频播放器。
 *
 * @author vipyinzhiwei
 * @since 2020/5/26
 */
class UgcDetailVideoPlayer : StandardGSYVideoPlayer {

    /**
     *  是否第一次加载视频。用于隐藏进度条、播放按钮等UI。播放完成后，重新加载视频，会重置为true。
     */
    private var initFirstLoad = true

    constructor(context: Context) : super(context)

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId() = R.layout.layout_ugc_detail_video_player

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                GSYVideoView.CURRENT_STATE_ERROR -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
                GSYVideoView.CURRENT_STATE_AUTO_COMPLETE -> imageView.setImageResource(R.drawable.ic_refresh_white_24dp)
                else -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
            }

        } else {
            super.updateStartImage()
        }
    }

    //正常
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        initFirstLoad = true
    }

    //准备中
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        mBottomContainer.gone()
    }

    //播放中
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        if (initFirstLoad) mBottomContainer.gone()
        initFirstLoad = false
    }

    //开始缓冲
    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        if (initFirstLoad) {
            mStartButton.gone()
            initFirstLoad = false
        } else {
            mStartButton.visible()
        }
    }

    //暂停
    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
    }

    //自动播放结束
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        mBottomContainer.gone()
    }

    //错误状态
    override fun changeUiToError() {
        super.changeUiToError()
    }

    fun getBottomContainer() = mBottomContainer
}