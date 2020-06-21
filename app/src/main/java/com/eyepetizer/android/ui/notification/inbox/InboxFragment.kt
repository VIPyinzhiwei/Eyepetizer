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

package com.eyepetizer.android.ui.notification.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eyepetizer.android.R
import com.eyepetizer.android.ui.common.ui.BaseFragment
import com.eyepetizer.android.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_notification_login_tips.*

/**
 * 通知-私信列表界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */
class InboxFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater.inflate(R.layout.fragment_notification_login_tips, container, false))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvLogin.setOnClickListener { LoginActivity.start(activity) }
    }

    companion object {

        fun newInstance() = InboxFragment()
    }

}
