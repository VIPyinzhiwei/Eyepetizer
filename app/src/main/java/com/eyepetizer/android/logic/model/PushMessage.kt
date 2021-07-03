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

package com.eyepetizer.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 通知-推送消息列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/2
 */
data class PushMessage(@SerializedName("messageList") val itemList: List<Message>, val nextPageUrl: String?, val updateTime: Long) : Model() {

    data class Message(
        val actionUrl: String,
        val content: String,
        val date: Long,
        val icon: String?,
        val id: Int,
        val ifPush: Boolean,
        val pushStatus: Int,
        val title: String?,
        val uid: Any,
        val viewed: Boolean
    )
}

