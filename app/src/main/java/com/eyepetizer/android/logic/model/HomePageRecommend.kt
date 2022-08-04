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
 * 首页-推荐列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/6
 */
data class HomePageRecommend(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(
        val actionUrl: String?,
        val ad: Boolean,
        val adTrack: Any,
        val author: Author,
        val backgroundImage: String,
        val brandWebsiteInfo: Any,
        val campaign: Any,
        val category: String,
        val collected: Boolean,
        val consumption: Consumption,
        val content: Content,
        val count: Int,
        val cover: Cover,
        val dataType: String,
        val date: Long,
        val description: String,
        val descriptionEditor: String,
        val descriptionPgc: Any,
        val duration: Int,
        val expert: Boolean,
        val favoriteAdTrack: Any,
        val follow: Author.Follow?,
        val footer: Any,
        val haveReward: Boolean,
        val header: Header,
        val icon: String,
        val iconType: String,
        val id: Long,
        val idx: Int,
        val ifLimitVideo: Boolean,
        val ifNewest: Boolean,
        val ifPgc: Boolean,
        val ifShowNotificationIcon: Boolean,
        val image: String,
        val itemList: List<ItemX>,
        val label: Label?,
        val labelList: List<Label>,
        val lastViewTime: Any,
        val library: String,
        val medalIcon: Boolean,
        val newestEndTime: Any,
        val playInfo: List<PlayInfo>,
        val playUrl: String,
        val played: Boolean,
        val playlists: Any,
        val promotion: Any,
        val provider: Provider,
        val reallyCollected: Boolean,
        val refreshUrl: String,
        val releaseTime: Long,
        val remark: Any,
        val resourceType: String,
        val rightText: String,
        val searchWeight: Int,
        val shareAdTrack: Any,
        val showHotSign: Boolean,
        val showImmediately: Boolean,
        val slogan: Any,
        val src: Int,
        val subTitle: Any,
        val subtitles: List<Subtitle>,
        val switchStatus: Boolean,
        val tags: List<Tag>,
        val text: String,
        val thumbPlayUrl: Any,
        val title: String,
        val titleList: List<String>,
        val titlePgc: Any,
        val topicTitle: String,
        val type: String,
        val uid: Int,
        val waterMarks: Any,
        val webAdTrack: Any,
        val webUrl: WebUrl,
        val detail: Discovery.AutoPlayVideoAdDetail?
    )

    data class Header(
        val actionUrl: String?,
        val cover: Any,
        val description: String,
        val font: Any,
        val icon: String,
        val iconType: String,
        val id: Int,
        val label: Label?,
        val labelList: List<Label>,
        val rightText: String,
        val showHateVideo: Boolean,
        val subTitle: Any,
        val subTitleFont: Any,
        val textAlign: String,
        val time: Long,
        val title: String
    )

    data class ItemX(val adIndex: Int, val `data`: DataXX, val id: Int, val tag: Any, val type: String)

    data class Subtitle(val type: String, val url: String)

    data class DataXX(
        val cover: Cover,
        val dailyResource: Boolean,
        val dataType: String,
        val id: Int,
        val nickname: String,
        val resourceType: String,
        val url: String,
        val urls: List<String>?,
        val userCover: String
    )
}
