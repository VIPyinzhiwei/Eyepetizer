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

package com.eyepetizer.android.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.ui.common.ui.WebViewActivity
import com.eyepetizer.android.ui.common.ui.WebViewActivity.Companion.DEFAULT_TITLE
import com.eyepetizer.android.ui.common.ui.WebViewActivity.Companion.DEFAULT_URL
import com.eyepetizer.android.util.GlobalUtil
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * 关于界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/24
 */
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun setupViews() {
        super.setupViews()
        tvTitle.text = GlobalUtil.getString(R.string.about)
        val version = "${GlobalUtil.getString(R.string.version)} ${GlobalUtil.appVersionName}"
        tvAboutVersion.text = version
        tvThanksTips.text = String.format(GlobalUtil.getString(R.string.thanks_to), GlobalUtil.appName)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvOpenSourceList.text = Html.fromHtml("<u>" + GlobalUtil.getString(R.string.open_source_project_list) + "</u>", 0)
        } else {
            tvOpenSourceList.text = Html.fromHtml("<u>" + GlobalUtil.getString(R.string.open_source_project_list) + "</u>")
        }
        ivLogo.setImageDrawable(GlobalUtil.getAppIcon())

        btnEncourage.setOnClickListener {
            MobclickAgent.onEvent(activity, Const.Mobclick.EVENT5)
            WebViewActivity.start(this, DEFAULT_TITLE, DEFAULT_URL, true)
        }
        tvOpenSourceList.setOnClickListener {
            OpenSourceProjectsActivity.start(this@AboutActivity)
        }
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }
}
