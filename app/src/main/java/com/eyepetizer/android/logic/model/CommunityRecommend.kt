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

/**
 * 社区-推荐列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/6
 */
data class CommunityRecommend(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(val adTrack: Any, val content: Content, val count: Int, val dataType: String, val footer: Any, val header: Header?, val itemList: List<ItemX>)

    data class Header(
        val actionUrl: String,
        val followType: String,
        val icon: String,
        val iconType: String,
        val id: Int,
        val issuerName: String,
        val labelList: Any,
        val showHateVideo: Boolean,
        val tagId: Int,
        val tagName: Any,
        val time: Long,
        val topShow: Boolean
    )

    data class Content(val adIndex: Int, val `data`: DataX, val id: Int, val tag: Any, val type: String)

    data class ItemX(val `data`: DataXX, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class DataX(
        val addWatermark: Boolean,
        val area: String,
        val checkStatus: String,
        val city: String,
        val collected: Boolean,
        val consumption: Consumption,
        val cover: Cover,
        val createTime: Long,
        val dataType: String,
        val description: String,
        val duration: Int,
        val height: Int,
        val id: Long,
        val ifMock: Boolean,
        val latitude: Double,
        val library: String,
        val longitude: Double,
        val owner: Owner,
        val playUrl: String,
        val playUrlWatermark: String,
        val privateMessageActionUrl: String,
        val reallyCollected: Boolean,
        val recentOnceReply: RecentOnceReply,
        val releaseTime: Long,
        val resourceType: String,
        val selectedTime: Long,
        val status: Any,
        val tags: List<Tag>?,
        val title: String,
        val transId: Any,
        val type: String,
        val uid: Int,
        val updateTime: Long,
        val url: String,
        val urls: List<String>?,
        val urlsWithWatermark: List<String>,
        val validateResult: String,
        val validateStatus: String,
        val validateTaskId: String,
        val width: Int
    )

    data class Owner(
        val actionUrl: String,
        val area: Any,
        val avatar: String,
        val birthday: Long,
        val city: String,
        val country: String,
        val cover: String,
        val description: String,
        val expert: Boolean,
        val followed: Boolean,
        val gender: String,
        val ifPgc: Boolean,
        val job: String,
        val library: String,
        val limitVideoOpen: Boolean,
        val nickname: String,
        val registDate: Long,
        val releaseDate: Long,
        val uid: Int,
        val university: String,
        val userType: String
    )

    data class RecentOnceReply(
        val actionUrl: String,
        val contentType: Any,
        val dataType: String,
        val message: String,
        val nickname: String
    )

    data class DataXX(
        val actionUrl: String?,
        val adTrack: List<Any>,
        val autoPlay: Boolean,
        val bgPicture: String,
        val dataType: String,
        val description: String,
        val header: HeaderX?,
        val id: Int,
        val image: String,
        val label: Label?,
        val labelList: List<Any>,
        val shade: Boolean,
        val subTitle: String,
        val title: String
    )

    data class HeaderX(
        val actionUrl: Any,
        val cover: Any,
        val description: Any,
        val font: Any,
        val icon: Any,
        val id: Int,
        val label: Any,
        val labelList: Any,
        val rightText: Any,
        val subTitle: Any,
        val subTitleFont: Any,
        val textAlign: String,
        val title: Any
    )
}