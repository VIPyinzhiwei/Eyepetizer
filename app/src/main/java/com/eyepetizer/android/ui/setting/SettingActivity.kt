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
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.ActivitySettingBinding
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.util.GlobalUtil
import kotlinx.android.synthetic.main.layout_title_bar.*

/**
 * 设置界面
 *
 * @author vipyinzhiwei
 * @since  2020/5/19
 */
class SettingActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(SettingViewModel::class.java) }

    private val binding by lazy { ActivitySettingBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun setupViews() {
        super.setupViews()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initTitle()
    }

    private fun initTitle() {
        tvTitle.text = GlobalUtil.getString(R.string.settings)
        tvRightText.setTextColor(ContextCompat.getColor(this@SettingActivity, R.color.white))
        tvRightText.textSize = 12f
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }
}