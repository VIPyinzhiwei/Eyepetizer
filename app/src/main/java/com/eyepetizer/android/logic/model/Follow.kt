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
 * 社区-关注列表，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/2
 */
data class Follow(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(val adTrack: List<Any>, val content: Content, val dataType: String, val header: Header, val type: String)

    data class Header(
        val actionUrl: String,
        val followType: String,
        val icon: String?,
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
}

data class Content(val adIndex: Int, val `data`: FollowCard, val id: Int, val tag: Any, val type: String)

data class FollowCard(
    val ad: Boolean,
    val adTrack: List<Any>,
    val author: Author?,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val id: Long,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: Any,
    val labelList: List<Any>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: List<PlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: Provider,
    val reallyCollected: Boolean,
    val releaseTime: Long?,
    val remark: String,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val subtitles: List<Any>,
    val tags: List<Tag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: WebUrl
)

data class PlayInfo(val height: Int, val name: String, val type: String, val url: String, val urlList: List<Url>, val width: Int)

data class Url(val name: String, val size: Int, val url: String)

data class Label(val actionUrl: String?, val text: String?, val card: String, val detail: Any?)