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

import java.lang.ref.WeakReference

/**
 * 数据传输工具类，处理Intent携带大量数据时，超过1MB限制出现的异常场景。
 *
 * @author vipyinzhiwei
 * @since  2020/5/25
 */
object IntentDataHolderUtil {

    private val map = hashMapOf<String, WeakReference<Any>>()

    fun setData(key: String, t: Any) {
        val value = WeakReference(t)
        map[key] = value
    }

    fun <T> getData(key: String): T? {
        val reference = map[key]
        return try {
            reference?.get() as T
        } catch (e: Exception) {
            null
        }
    }
}