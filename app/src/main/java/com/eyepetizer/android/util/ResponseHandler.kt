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

package com.eyepetizer.android.util

import com.eyepetizer.android.R
import com.eyepetizer.android.ui.common.exception.ResponseCodeException
import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 获取网络请求返回的异常信息。
 *
 * @author vipyinzhiwei
 * @since  2020/5/29
 */
object ResponseHandler {

    /**
     * 当网络请求没有正常响应的时候，根据异常类型进行相应的处理。
     * @param e 异常实体类
     */
    fun getFailureTips(e: Throwable?) = when (e) {
        is ConnectException -> GlobalUtil.getString(R.string.network_connect_error)
        is SocketTimeoutException -> GlobalUtil.getString(R.string.network_connect_timeout)
        is ResponseCodeException -> GlobalUtil.getString(R.string.network_response_code_error) + e.responseCode
        is NoRouteToHostException -> GlobalUtil.getString(R.string.no_route_to_host)
        is UnknownHostException -> GlobalUtil.getString(R.string.network_error)
        is JsonSyntaxException -> GlobalUtil.getString(R.string.json_data_error)
        else -> {
            GlobalUtil.getString(R.string.unknown_error)
        }
    }
}