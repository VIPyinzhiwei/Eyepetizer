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

package com.eyepetizer.android.ui.community.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.FragmentRefreshLayoutBinding
import com.eyepetizer.android.event.MessageEvent
import com.eyepetizer.android.event.RefreshEvent
import com.eyepetizer.android.extension.gone
import com.eyepetizer.android.extension.showToast
import com.eyepetizer.android.extension.visible
import com.eyepetizer.android.ui.common.callback.AutoPlayScrollListener
import com.eyepetizer.android.ui.common.ui.BaseFragment
import com.eyepetizer.android.util.GlobalUtil
import com.eyepetizer.android.util.InjectorUtil
import com.eyepetizer.android.util.ResponseHandler
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.shuyu.gsyvideoplayer.GSYVideoManager

/**
 * 社区-关注列表界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */
class FollowFragment : BaseFragment() {

    private var _binding: FragmentRefreshLayoutBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this, InjectorUtil.getFollowViewModelFactory()).get(FollowViewModel::class.java) }

    private lateinit var adapter: FollowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRefreshLayoutBinding.inflate(layoutInflater, container, false)
        return super.onCreateView(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowAdapter(this, viewModel.dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.addOnScrollListener(AutoPlayScrollListener(R.id.videoPlayer, AutoPlayScrollListener.PLAY_RANGE_TOP, AutoPlayScrollListener.PLAY_RANGE_BOTTOM))
        binding.refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        binding.refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }
        binding.refreshLayout.gone()
        observe()
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
        _binding = null
    }

    override fun loadDataOnce() {
        super.loadDataOnce()
        startLoading()
    }

    override fun startLoading() {
        super.startLoading()
        viewModel.onRefresh()
        binding.refreshLayout.gone()
    }

    override fun loadFinished() {
        super.loadFinished()
        binding.refreshLayout.visible()
    }

    @CallSuper
    override fun loadFailed(msg: String?) {
        super.loadFailed(msg)
        showLoadErrorView(msg ?: GlobalUtil.getString(R.string.unknown_error)) { startLoading() }
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        if (messageEvent is RefreshEvent && javaClass == messageEvent.activityClass) {
            binding.refreshLayout.autoRefresh()
            if (binding.recyclerView.adapter?.itemCount ?: 0 > 0) binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            val response = result.getOrNull()
            if (response == null) {
                ResponseHandler.getFailureTips(result.exceptionOrNull()).let { if (viewModel.dataList.isNullOrEmpty()) loadFailed(it) else it.showToast() }
                binding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            loadFinished()
            viewModel.nextPageUrl = response.nextPageUrl
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                binding.refreshLayout.closeHeaderOrFooter()
                return@Observer
            }
            if (response.itemList.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                binding.refreshLayout.finishLoadMoreWithNoMoreData()
                return@Observer
            }
            when (binding.refreshLayout.state) {
                RefreshState.None, RefreshState.Refreshing -> {
                    viewModel.dataList.clear()
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyDataSetChanged()
                }
                RefreshState.Loading -> {
                    val itemCount = viewModel.dataList.size
                    viewModel.dataList.addAll(response.itemList)
                    adapter.notifyItemRangeInserted(itemCount, response.itemList.size)
                }
                else -> {
                }
            }
            if (response.nextPageUrl.isNullOrEmpty()) {
                binding.refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                binding.refreshLayout.closeHeaderOrFooter()
            }
        })
    }

    companion object {

        fun newInstance() = FollowFragment()
    }
}