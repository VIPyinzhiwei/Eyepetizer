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
 * 首页-发现列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/9
 */
data class Discovery(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(
        val actionUrl: String?,
        val ad: Boolean,
        val adTrack: Any,
        val author: Author,
        val content: Content,
        val autoPlay: Boolean,
        val brandWebsiteInfo: Any,
        val campaign: Any,
        val category: String,
        val collected: Boolean,
        val consumption: Consumption,
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
        val releaseTime: Long,
        val remark: Any,
        val resourceType: String,
        val rightText: String,
        val searchWeight: Int,
        val shade: Boolean,
        val shareAdTrack: Any,
        val slogan: Any,
        val src: Any,
        val subTitle: Any,
        val subtitles: List<Any>,
        val switchStatus: Boolean,
        val tags: List<Tag>,
        val text: String,
        val thumbPlayUrl: Any,
        val title: String,
        val titlePgc: Any,
        val type: String,
        val uid: Int,
        val waterMarks: Any,
        val webAdTrack: Any,
        val webUrl: WebUrl,
        val detail: AutoPlayVideoAdDetail?
    )

    data class Header(
        val actionUrl: String?,
        val cover: Any,
        val font: String,
        val id: Int,
        val label: Any,
        val labelList: Any,
        val rightText: String,
        val subTitle: Any,
        val subTitleFont: Any,
        val textAlign: String,
        val title: String,
        val icon: String,
        val description: String
    )

    data class ItemX(val adIndex: Int, val `data`: DataX, val id: Int, val tag: Any, val type: String)

    data class DataX(
        val actionUrl: String,
        val adTrack: List<Any>,
        val autoPlay: Boolean,
        val dataType: String,
        val description: String,
        val header: HeaderX,
        val id: Int,
        val image: String,
        val label: Label?,
        val labelList: List<Any>,
        val shade: Boolean,
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

    data class AutoPlayVideoAdDetail(
        val actionUrl: String,
        val adTrack: List<Any>,
        val adaptiveImageUrls: String,
        val adaptiveUrls: String,
        val canSkip: Boolean,
        val categoryId: Int,
        val countdown: Boolean,
        val cycleCount: Int,
        val description: String,
        val displayCount: Int,
        val displayTimeDuration: Int,
        val icon: String,
        val id: Long,
        val ifLinkage: Boolean,
        val imageUrl: String,
        val iosActionUrl: String,
        val linkageAdId: Int,
        val loadingMode: Int,
        val openSound: Boolean,
        val position: Int,
        val showActionButton: Boolean,
        val showImage: Boolean,
        val showImageTime: Int,
        val timeBeforeSkip: Int,
        val title: String,
        val url: String,
        val videoAdType: String,
        val videoType: String
    )
}