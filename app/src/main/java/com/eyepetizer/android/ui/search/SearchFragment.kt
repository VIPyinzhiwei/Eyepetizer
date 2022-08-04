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

package com.eyepetizer.android.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.FragmentSearchBinding
import com.eyepetizer.android.extension.logW
import com.eyepetizer.android.extension.setDrawable
import com.eyepetizer.android.extension.showToast
import com.eyepetizer.android.extension.visibleAlphaAnimation
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.ui.common.ui.BaseFragment
import com.eyepetizer.android.util.InjectorUtil
import com.umeng.analytics.MobclickAgent

/**
 * 搜索界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/26
 */
class SearchFragment : BaseFragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this, InjectorUtil.getSearchViewModelFactory()).get(SearchViewModel::class.java) }

    private lateinit var adapter: HotSearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return super.onCreateView(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llSearch.visibleAlphaAnimation(500)
        binding.etQuery.setDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_search_gray_17dp), 14f, 14f)
        binding.etQuery.setOnEditorActionListener(EditorActionListener(activity, binding))
        binding.tvCancel.setOnClickListener {
            hideSoftKeyboard()
            removeFragment(activity, this)
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = HotSearchAdapter(this, viewModel.dataList)
        binding.recyclerView.adapter = adapter
        viewModel.onRefresh()
        observe()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideSoftKeyboard()
        _binding = null
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.anl_push_up_in)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.anl_push_top_out)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.dataListLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding.etQuery.showSoftKeyboard()
            val response = result.getOrNull()
            if (response == null) {
                result.exceptionOrNull()?.printStackTrace()
                return@Observer
            }
            if (response.isNullOrEmpty() && viewModel.dataList.isEmpty()) {
                return@Observer
            }
            if (response.isNullOrEmpty() && viewModel.dataList.isNotEmpty()) {
                return@Observer
            }
            viewModel.dataList.clear()
            viewModel.dataList.addAll(response)
            adapter.notifyDataSetChanged()
        })
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyboard() {
        try {
            activity.currentFocus?.run {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            logW(TAG, e.message, e)
        }
    }

    /**
     * 拉起软键盘
     */
    private fun View.showSoftKeyboard() {
        try {
            this.isFocusable = true
            this.isFocusableInTouchMode = true
            this.requestFocus()
            val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(this, 0)
        } catch (e: Exception) {
            logW(TAG, e.message, e)
        }
    }

    class EditorActionListener(val activity: Context, val binding: FragmentSearchBinding) : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                MobclickAgent.onEvent(activity, Const.Mobclick.EVENT3)
                if (binding.etQuery.text.toString().isEmpty()) {
                    R.string.input_keywords_tips.showToast()
                    return false
                }
                R.string.currently_not_supported.showToast()
                return true
            }
            return true
        }
    }

    companion object {

        /**
         * 切换Fragment，会加入回退栈。
         */
        fun switchFragment(activity: Activity) {
            (activity as BaseActivity).supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SearchFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        /**
         * 先移除Fragment，并将Fragment从堆栈弹出。
         */
        fun removeFragment(activity: Activity, fragment: Fragment) {
            (activity as BaseActivity).supportFragmentManager.run {
                beginTransaction().remove(fragment).commitAllowingStateLoss()
                popBackStack()
            }
        }
    }
}