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

import android.content.Context
import android.content.Intent
import com.tencent.sonic.sdk.SonicConstants
import com.tencent.sonic.sdk.SonicSession
import com.tencent.sonic.sdk.SonicSessionConnection
import java.io.BufferedInputStream
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

class OfflinePkgSessionConnection(context: Context, session: SonicSession?, intent: Intent?) : SonicSessionConnection(session, intent) {

    private val context: WeakReference<Context>

    override fun internalConnect(): Int {
        val ctx = context.get()
        if (null != ctx) {
            try {
                val offlineHtmlInputStream = ctx.assets.open("sonic-demo-index.html")
                responseStream = BufferedInputStream(offlineHtmlInputStream)
                return SonicConstants.ERROR_CODE_SUCCESS
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return SonicConstants.ERROR_CODE_UNKNOWN
    }

    override fun internalGetResponseStream(): BufferedInputStream {
        return responseStream
    }

    override fun internalGetCustomHeadFieldEtag(): String {
        return CUSTOM_HEAD_FILED_ETAG
    }

    override fun disconnect() {
        if (null != responseStream) {
            try {
                responseStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun getResponseCode(): Int {
        return 200
    }

    override fun getResponseHeaderFields(): Map<String, List<String>> {
        return HashMap(0)
    }

    override fun getResponseHeaderField(key: String?): String {
        return ""
    }

    init {
        this.context = WeakReference(context)
    }
}