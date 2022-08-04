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

package com.eyepetizer.android.logic

import com.eyepetizer.android.logic.dao.VideoDao
import com.eyepetizer.android.logic.model.VideoDetail
import com.eyepetizer.android.logic.network.EyepetizerNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * 视频相关，对应的仓库数据管理。
 *
 * @author vipyinzhiwei
 * @since  2020/5/15
 */
class VideoRepository(private val dao: VideoDao, private val network: EyepetizerNetwork) {

    suspend fun refreshVideoReplies(repliesUrl: String) = requestVideoReplies(repliesUrl)

    suspend fun refreshVideoRelatedAndVideoReplies(videoId: Long, repliesUrl: String) = requestVideoRelatedAndVideoReplies(videoId, repliesUrl)

    suspend fun refreshVideoDetail(videoId: Long, repliesUrl: String) = requestVideoDetail(videoId, repliesUrl)

    private suspend fun requestVideoReplies(url: String) = withContext(Dispatchers.IO) {
        val deferredVideoReplies = async { network.fetchVideoReplies(url) }
        val videoReplies = deferredVideoReplies.await()
        videoReplies
    }

    private suspend fun requestVideoRelatedAndVideoReplies(videoId: Long, repliesUrl: String) = withContext(Dispatchers.IO) {
        val deferredVideoRelated = async { network.fetchVideoRelated(videoId) }
        val deferredVideoReplies = async { network.fetchVideoReplies(repliesUrl) }
        val videoRelated = deferredVideoRelated.await()
        val videoReplies = deferredVideoReplies.await()
        val videoDetail = VideoDetail(null, videoRelated, videoReplies)
        videoDetail
    }

    private suspend fun requestVideoDetail(videoId: Long, repliesUrl: String) = withContext(Dispatchers.IO) {
        val deferredVideoRelated = async { network.fetchVideoRelated(videoId) }
        val deferredVideoReplies = async { network.fetchVideoReplies(repliesUrl) }
        val deferredVideoBeanForClient = async { network.fetchVideoBeanForClient(videoId) }
        val videoBeanForClient = deferredVideoBeanForClient.await()
        val videoRelated = deferredVideoRelated.await()
        val videoReplies = deferredVideoReplies.await()
        val videoDetail = VideoDetail(videoBeanForClient, videoRelated, videoReplies)
        dao.cacheVideoDetail(videoDetail)
        videoDetail
    }

    companion object {

        @Volatile
        private var INSTANCE: VideoRepository? = null

        fun getInstance(dao: VideoDao, network: EyepetizerNetwork): VideoRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: VideoRepository(dao, network)
        }
    }

}