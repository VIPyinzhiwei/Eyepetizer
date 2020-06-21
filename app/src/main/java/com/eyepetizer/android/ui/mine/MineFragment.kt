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
import androidx.multidex.BuildConfig
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
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
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 我的界面。
 *
 * @author vipyinzhiwei
 * @since  2020/4/29
 */
class MineFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_mine, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvVersionNumber.text = String.format(GlobalUtil.getString(R.string.version_show), GlobalUtil.eyepetizerVersionName)
        setOnClickListener(
            ivMore, ivAvatar, tvLoginTips, tvFavorites, tvCache, tvFollow, tvWatchRecord, tvNotificationToggle,
            tvMyBadge, tvFeedback, tvContribute, tvVersionNumber, rootView, llScrollViewContent
        ) {
            when (this) {
                ivMore -> SettingActivity.start(activity)

                ivAvatar, tvLoginTips, tvFavorites, tvCache, tvFollow, tvWatchRecord, tvNotificationToggle, tvMyBadge -> {
                    LoginActivity.start(activity)
                }
                tvContribute -> {
                    WebViewActivity.start(activity, WebViewActivity.DEFAULT_TITLE, Const.Url.AUTHOR_OPEN, false, false)
                }
                tvFeedback -> {
                    WebViewActivity.start(activity, WebViewActivity.DEFAULT_TITLE, WebViewActivity.DEFAULT_URL, true, false, MODE_SONIC_WITH_OFFLINE_CACHE)
                }
                tvVersionNumber -> {
                    WebViewActivity.start(activity, WebViewActivity.DEFAULT_TITLE, WebViewActivity.DEFAULT_URL, true, false, MODE_SONIC_WITH_OFFLINE_CACHE)
                }
                this@MineFragment.rootView, llScrollViewContent -> {
                    MobclickAgent.onEvent(activity, Const.Mobclick.EVENT4)
                    AboutActivity.start(activity)
                }

                else -> {
                }
            }
        }

        tvVersionNumber.setOnLongClickListener {
            String.format(GlobalUtil.getString(R.string.build_type), BuildConfig.BUILD_TYPE).showToast()
            true
        }
    }

    companion object {

        fun newInstance() = MineFragment()
    }
}
