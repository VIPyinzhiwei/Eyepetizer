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

package com.eyepetizer.android.logic.network.api

import com.eyepetizer.android.logic.model.VideoBeanForClient
import com.eyepetizer.android.logic.model.VideoRelated
import com.eyepetizer.android.logic.model.VideoReplies
import com.eyepetizer.android.logic.network.ServiceCreator
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * 与视频页面相关API请求。
 *
 * @author vipyinzhiwei
 * @since  2020/5/14
 */
interface VideoService {

    /**
     * 视频详情-视频信息
     */
    @GET("api/v2/video/{id}")
    suspend fun getVideoBeanForClient(@Path("id") videoId: Long): VideoBeanForClient

    /**
     * 视频详情-推荐列表
     */
    @GET("api/v4/video/related")
    suspend fun getVideoRelated(@Query("id") videoId: Long): VideoRelated

    /**
     * 视频详情-评论列表
     */
    @GET
    suspend fun getVideoReplies(@Url url: String): VideoReplies

    companion object {

        /**
         * 视频详情-评论列表URL
         */
        const val VIDEO_REPLIES_URL = "${ServiceCreator.BASE_URL}api/v2/replies/video?videoId="
    }

}