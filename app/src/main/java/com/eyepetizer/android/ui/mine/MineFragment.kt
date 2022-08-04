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

package com.eyepetizer.android.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyepetizer.android.BuildConfig
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.FragmentMineBinding
import com.eyepetizer.android.extension.setOnClickListener
import com.eyepetizer.android.extension.showToast
import com.eyepetizer.android.ui.common.ui.BaseFragment
import com.eyepetizer.android.ui.common.ui.WebViewActivity
import com.eyepetizer.android.ui.common.ui.WebViewActivity.Companion.MODE_SONIC_WITH_OFFLINE_CACHE
import com.eyepetizer.android.ui.login.LoginActivity
import com.eyepetizer.android.ui.setting.AboutActivity
import com.eyepetizer.android.ui.setting.SettingActivity
import com.eyepetizer.android.util.GlobalUtil
import com.umeng.analytics.MobclickAgent

/**
 * 我的界面。
 *
 * @author vipyinzhiwei
 * @since  2020/4/29
 */
class MineFragment : BaseFragment() {

    private var _binding: FragmentMineBinding? = null

    private val binding: FragmentMineBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMineBinding.inflate(inflater, container, false)
        return super.onCreateView(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvVersionNumber.text = String.format(GlobalUtil.getString(R.string.version_show), GlobalUtil.eyepetizerVersionName)
        setOnClickListener(
            binding.ivMore, binding.ivAvatar, binding.tvLoginTips, binding.tvFavorites, binding.tvCache, binding.tvFollow, binding.tvWatchRecord,
            binding.tvNotificationToggle, binding.tvMyBadge, binding.tvFeedback, binding.tvContribute, binding.tvVersionNumber, rootView, binding.llScrollViewContent
        ) {
            when (this) {
                binding.ivMore -> SettingActivity.start(activity)

                binding.ivAvatar, binding.tvLoginTips, binding.tvFavorites, binding.tvCache, binding.tvFollow, binding.tvWatchRecord, binding.tvNotificationToggle, binding.tvMyBadge -> {
                    LoginActivity.start(activity)
                }
                binding.tvContribute -> {
                    WebViewActivity.start(activity, WebViewActivity.DEFAULT_TITLE, Const.Url.AUTHOR_OPEN, false, false)
                }
                binding.tvFeedback -> {
                    WebViewActivity.start(activity, WebViewActivity.DEFAULT_TITLE, WebViewActivity.DEFAULT_URL, true, false, MODE_SONIC_WITH_OFFLINE_CACHE)
                }
                binding.tvVersionNumber -> {
                    WebViewActivity.start(activity, WebViewActivity.DEFAULT_TITLE, WebViewActivity.DEFAULT_URL, true, false, MODE_SONIC_WITH_OFFLINE_CACHE)
                }
                this@MineFragment.rootView, binding.llScrollViewContent -> {
                    MobclickAgent.onEvent(activity, Const.Mobclick.EVENT4)
                    AboutActivity.start(activity)
                }
            }
        }

        binding.tvVersionNumber.setOnLongClickListener {
            val channel = String.format(GlobalUtil.getString(R.string.channel), GlobalUtil.getApplicationMetaData("UMENG_CHANNEL"))
            val buildType = String.format(GlobalUtil.getString(R.string.build_type), BuildConfig.BUILD_TYPE)
            val versionName = String.format(GlobalUtil.getString(R.string.version_name), BuildConfig.VERSION_NAME)
            "${channel}\n${buildType}\n${versionName}".showToast()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance() = MineFragment()
    }
}
