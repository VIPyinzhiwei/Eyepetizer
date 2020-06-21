/*
 * Copyright (C) 2017 THL A29 Limited, a Tencent company.  All rights reserved.
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
package com.eyepetizer.android.ui.common.ui.vassonic

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import org.json.JSONObject

/**
 * Sonic javaScript Interface (Android API Level >= 17)
 */
class SonicJavaScriptInterface(private val sessionClient: SonicSessionClientImpl?, private val intent: Intent) {

    // the callback function of demo page is hardcode as 'getDiffDataCallback'
    @get:JavascriptInterface
    val diffData: Unit
        get() {
            // the callback function of demo page is hardcode as 'getDiffDataCallback'
            getDiffData2("getDiffDataCallback")
        }

    @JavascriptInterface
    fun getDiffData2(jsCallbackFunc: String) {
        sessionClient?.getDiffData { resultData ->
            val callbackRunnable = Runnable {
                val jsCode = "javascript:" + jsCallbackFunc + "('" + toJsString(resultData) + "')"
                sessionClient.webView?.loadUrl(jsCode)
            }
            if (Looper.getMainLooper() == Looper.myLooper()) {
                callbackRunnable.run()
            } else {
                Handler(Looper.getMainLooper()).post(callbackRunnable)
            }
        }
    }

    @get:JavascriptInterface
    val performance: String
        get() {
            val clickTime = intent.getLongExtra(PARAM_CLICK_TIME, -1)
            val loadUrlTime = intent.getLongExtra(PARAM_LOAD_URL_TIME, -1)
            try {
                val result = JSONObject()
                result.put(PARAM_CLICK_TIME, clickTime)
                result.put(PARAM_LOAD_URL_TIME, loadUrlTime)
                return result.toString()
            } catch (e: Exception) {
            }
            return ""
        }

    companion object {
        const val PARAM_CLICK_TIME = "clickTime"
        const val PARAM_LOAD_URL_TIME = "loadUrlTime"

        /**
         * From RFC 4627, "All Unicode characters may be placed within the quotation marks except
         * for the characters that must be escaped: quotation mark,
         * reverse solidus, and the control characters (U+0000 through U+001F)."
         */
        private fun toJsString(value: String?): String {
            if (value == null) {
                return "null"
            }
            val out = StringBuilder(1024)
            var i = 0
            val length = value.length
            while (i < length) {
                val c = value[i]
                when (c) {
                    '"', '\\', '/' -> out.append('\\').append(c)
                    '\t' -> out.append("\\t")
                    '\b' -> out.append("\\b")
                    '\n' -> out.append("\\n")
                    '\r' -> out.append("\\r")
                    '\u000C' -> out.append("\\f")   //Kotlin 转义字符 - 换页符 "\f" 提示错误 Illegal escape: '\f' 解决方案:https://www.jianshu.com/p/a59a24f76c4b,使用 Kotlin "\u000C",使用 Java "\f"
                    else -> if (c.toInt() <= 0x1F) {
                        out.append(String.format("\\u%04x", c.toInt()))
                    } else {
                        out.append(c)
                    }
                }
                i++
            }
            return out.toString()
        }
    }

}