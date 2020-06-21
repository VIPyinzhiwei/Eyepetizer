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
 *
 * 视频详情-相关推荐+评论，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/16
 */
data class VideoDetail(val videoBeanForClient: VideoBeanForClient?, val videoRelated: VideoRelated?, val videoReplies: VideoReplies)


/**
 * 视频详情-相关推荐，响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/14
 */
data class VideoRelated(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(
        val actionUrl: String,
        val ad: Boolean,
        val adTrack: Any,
        val author: Author,
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
        val descriptionPgc: String,
        val duration: Int,
        val favoriteAdTrack: Any,
        val follow: Author.Follow?,
        val footer: Any,
        val header: Any,
        val id: Long,
        val idx: Int,
        val ifLimitVideo: Boolean,
        val itemList: List<ItemX>,
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
        val recallSource: String,
        val releaseTime: Long,
        val remark: String,
        val resourceType: String,
        val searchWeight: Int,
        val shareAdTrack: Any,
        val slogan: Any,
        val src: Int,
        val subTitle: Any,
        val subtitles: List<Any>,
        val tags: List<Tag>,
        val text: String,
        val thumbPlayUrl: Any,
        val title: String,
        val titlePgc: String,
        val type: String,
        val waterMarks: Any,
        val webAdTrack: Any,
        val webUrl: WebUrl
    )

    data class ItemX(val adIndex: Int, val `data`: DataX, val id: Int, val tag: Any, val type: String)

    data class DataX(val actionUrl: Any, val contentType: String, val dataType: String, val message: String, val nickname: String)
}

/**
 * 视频详情-评论列表。响应实体类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/14
 */
data class VideoReplies(val itemList: List<Item>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) : Model() {

    data class Item(val `data`: Data, val type: String, val tag: Any?, val id: Int = 0, val adIndex: Int)

    data class Data(
        val actionUrl: String?,
        val adTrack: Any,
        val cover: Any,
        val createTime: Long,
        val dataType: String,
        val follow: Author.Follow?,
        val hot: Boolean,
        val id: Long,
        val imageUrl: Any,
        val likeCount: Int,
        val liked: Boolean,
        val message: String,
        val parentReply: ParentReply?,
        val parentReplyId: Long,
        val replyStatus: String,
        val rootReplyId: Long,
        val sequence: Int,
        val showConversationButton: Boolean,
        val showParentReply: Boolean,
        val sid: String,
        val subTitle: Any,
        val text: String,
        val type: String,
        val ugcVideoId: Any,
        val ugcVideoUrl: Any,
        val user: User?,
        val userBlocked: Boolean,
        val userType: Any,
        val videoId: Long,
        val videoTitle: String
    )

    data class ParentReply(val id: Long, val imageUrl: Any, val message: String, val replyStatus: String, val user: User?)

    data class User(
        val actionUrl: String,
        val area: Any,
        val avatar: String?,
        val birthday: Long,
        val city: Any,
        val country: Any,
        val cover: String,
        val description: String,
        val expert: Boolean,
        val followed: Boolean,
        val gender: String,
        val ifPgc: Boolean,
        val job: Any,
        val library: String,
        val limitVideoOpen: Boolean,
        val nickname: String,
        val registDate: Long,
        val releaseDate: Long,
        val uid: Int,
        val university: Any,
        val userType: String
    )
}

