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

package com.eyepetizer.android.logic.network

import com.eyepetizer.android.logic.network.api.MainPageService
import com.eyepetizer.android.logic.network.api.VideoService

/**
 * 管理所有网络请求。
 *
 * @author vipyinzhiwei
 * @since  2020/5/2
 */
class EyepetizerNetwork {

    private val mainPageService = ServiceCreator.create(MainPageService::class.java)

    private val videoService = ServiceCreator.create(VideoService::class.java)

    suspend fun fetchDiscovery(url: String) = mainPageService.getDiscovery(url)

    suspend fun fetchHomePageRecommend(url: String) = mainPageService.getHomePageRecommend(url)

    suspend fun fetchDaily(url: String) = mainPageService.getDaily(url)

    suspend fun fetchCommunityRecommend(url: String) = mainPageService.getCommunityRecommend(url)

    suspend fun fetchFollow(url: String) = mainPageService.gethFollow(url)

    suspend fun fetchPushMessage(url: String) = mainPageService.getPushMessage(url)

    suspend fun fetchHotSearch() = mainPageService.getHotSearch()

    suspend fun fetchVideoBeanForClient(videoId: Long) = videoService.getVideoBeanForClient(videoId)

    suspend fun fetchVideoRelated(videoId: Long) = videoService.getVideoRelated(videoId)

    suspend fun fetchVideoReplies(url: String) = videoService.getVideoReplies(url)

    companion object {

        private var network: EyepetizerNetwork? = null

        fun getInstance(): EyepetizerNetwork {
            if (network == null) {
                synchronized(EyepetizerNetwork::class.java) {
                    if (network == null) {
                        network = EyepetizerNetwork()
                    }
                }
            }
            return network!!
        }
    }
}