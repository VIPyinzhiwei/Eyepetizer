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

package com.eyepetizer.android.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.ActivityLoginBinding
import com.eyepetizer.android.extension.*
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.ui.common.ui.WebViewActivity
import com.eyepetizer.android.util.GlobalUtil

/**
 * 登录界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/19
 */
class LoginActivity : BaseActivity() {

    private var _binding: ActivityLoginBinding? = null

    private val binding: ActivityLoginBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarBackground(R.color.black)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setupViews() {
        super.setupViews()
        initTitleBar()
        initListener()
    }

    private fun initTitleBar() {
        binding.titleBar.titleBar.layoutParams.height = resources.getDimensionPixelSize(R.dimen.actionBarSizeSecondary)
        binding.titleBar.titleBar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        val padding = dp2px(9f)
        binding.titleBar.ivNavigateBefore.setPadding(padding, padding, padding, padding)
        binding.titleBar.ivNavigateBefore.setImageResource(R.drawable.ic_close_white_24dp)
        binding.titleBar.tvRightText.visible()
        binding.titleBar.tvRightText.text = GlobalUtil.getString(R.string.forgot_password)
        binding.titleBar.tvRightText.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
        binding.titleBar.tvRightText.textSize = 12f
        binding.titleBar.divider.gone()
        binding.etPhoneNumberOrEmail.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_person_white_18dp), 18f, 18f, 0)
        binding.etPassWord.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_password_white_lock_18dp), 18f, 18f, 0)
    }

    private fun initListener() {
        setOnClickListener(
            binding.titleBar.tvRightText, binding.tvUserLogin, binding.tvUserRegister, binding.tvAuthorLogin, binding.tvUserAgreement, binding.tvUserLogin,
            binding.ivWechat, binding.ivSina, binding.ivQQ
        ) {
            when (this) {
                binding.titleBar.tvRightText -> {
                    WebViewActivity.start(this@LoginActivity, WebViewActivity.DEFAULT_TITLE, Const.Url.FORGET_PASSWORD, false, false)
                }
                binding.tvUserRegister -> {
                    WebViewActivity.start(this@LoginActivity, WebViewActivity.DEFAULT_TITLE, Const.Url.AUTHOR_REGISTER, false, false)
                }
                binding.tvAuthorLogin -> {
                    WebViewActivity.start(this@LoginActivity, WebViewActivity.DEFAULT_TITLE, Const.Url.AUTHOR_LOGIN, false, false)
                }
                binding.tvUserAgreement -> {
                    WebViewActivity.start(this@LoginActivity, WebViewActivity.DEFAULT_TITLE, Const.Url.USER_AGREEMENT, false, false)
                }
                binding.tvUserLogin, binding.ivWechat, binding.ivSina, binding.ivQQ -> {
                    R.string.currently_not_supported.showToast()
                }
            }
        }
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}