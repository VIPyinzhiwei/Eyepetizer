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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.ActivityUgcDetailBinding
import com.eyepetizer.android.extension.showToast
import com.eyepetizer.android.logic.model.CommunityRecommend
import com.eyepetizer.android.ui.common.callback.AutoPlayPageChangeListener
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.util.GlobalUtil
import com.eyepetizer.android.util.IntentDataHolderUtil
import com.shuyu.gsyvideoplayer.GSYVideoManager

/**
 * 社区-推荐详情页。
 *
 * @author vipyinzhiwei
 * @since  2020/5/24
 */
class UgcDetailActivity : BaseActivity() {

    private var _binding: ActivityUgcDetailBinding? = null

    private val binding: ActivityUgcDetailBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this).get(UgcDetailViewModel::class.java) }

    private lateinit var adapter: UgcDetailAdapter

    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUgcDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setContentView(layoutView: View) {
        if (checkArguments()) {
            super.setContentView(layoutView)
            setStatusBarBackground(R.color.black)
        }
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        onPageChangeCallback?.run { binding.viewPager.unregisterOnPageChangeCallback(this) }
        onPageChangeCallback = null
        _binding = null
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.anl_push_bottom_out)
    }

    override fun setupViews() {
        super.setupViews()
        if (viewModel.dataList == null) {
            viewModel.itemPosition = getCurrentItemPosition()
            viewModel.dataList = IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(EXTRA_RECOMMEND_ITEM_LIST_JSON)
        }
        if (viewModel.dataList == null) {
            GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
            finish()
        } else {
            adapter = UgcDetailAdapter(this, viewModel.dataList!!)
            binding.viewPager.adapter = adapter
            binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
            binding.viewPager.offscreenPageLimit = 1
            onPageChangeCallback = AutoPlayPageChangeListener(binding.viewPager, viewModel.itemPosition, R.id.videoPlayer)
            binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback!!)
            binding.viewPager.setCurrentItem(viewModel.itemPosition, false)
        }
    }

    private fun checkArguments() = if (IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(EXTRA_RECOMMEND_ITEM_LIST_JSON).isNullOrEmpty()
        || IntentDataHolderUtil.getData<CommunityRecommend.Item>(EXTRA_RECOMMEND_ITEM_JSON) == null
    ) {
        GlobalUtil.getString(R.string.jump_page_unknown_error).showToast()
        finish()
        false
    } else {
        true
    }

    private fun getCurrentItemPosition(): Int {
        val list = IntentDataHolderUtil.getData<List<CommunityRecommend.Item>>(EXTRA_RECOMMEND_ITEM_LIST_JSON)
        val currentItem = IntentDataHolderUtil.getData<CommunityRecommend.Item>(EXTRA_RECOMMEND_ITEM_JSON)
        list?.forEachIndexed { index, item ->
            if (currentItem == item) {
                viewModel.itemPosition = index
                return@forEachIndexed
            }
        }
        return viewModel.itemPosition
    }

    companion object {

        const val TAG = "UgcDetailActivity"
        const val EXTRA_RECOMMEND_ITEM_LIST_JSON = "recommend_item_list"
        const val EXTRA_RECOMMEND_ITEM_JSON = "recommend_item"

        fun start(context: Activity, dataList: List<CommunityRecommend.Item>, currentItem: CommunityRecommend.Item) {
            IntentDataHolderUtil.setData(EXTRA_RECOMMEND_ITEM_LIST_JSON, dataList)
            IntentDataHolderUtil.setData(EXTRA_RECOMMEND_ITEM_JSON, currentItem)
            val starter = Intent(context, UgcDetailActivity::class.java)
            context.startActivity(starter)
            context.overridePendingTransition(R.anim.anl_push_bottom_in, R.anim.anl_push_up_out)
        }
    }
}
