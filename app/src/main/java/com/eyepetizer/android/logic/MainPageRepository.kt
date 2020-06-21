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

import com.eyepetizer.android.logic.dao.MainPageDao
import com.eyepetizer.android.logic.network.EyepetizerNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 主页界面，主要包含：（首页，社区，通知，我的），对应的仓库数据管理。
 *
 * @author vipyinzhiwei
 * @since  2020/5/2
 */
class MainPageRepository private constructor(private val mainPageDao: MainPageDao, private val eyepetizerNetwork: EyepetizerNetwork) {

    suspend fun refreshDiscovery(url: String) = requestDiscovery(url)

    suspend fun refreshHomePageRecommend(url: String) = requestHomePageRecommend(url)

    suspend fun refreshDaily(url: String) = requestDaily(url)

    suspend fun refreshCommunityRecommend(url: String) = requestCommunityRecommend(url)

    suspend fun refreshFollow(url: String) = requestFollow(url)

    suspend fun refreshPushMessage(url: String) = requestPushMessage(url)

    suspend fun refreshHotSearch() = requestHotSearch()

    private suspend fun requestDiscovery(url: String) = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchDiscovery(url)
        mainPageDao.cacheDiscovery(response)
        response
    }

    private suspend fun requestHomePageRecommend(url: String) = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchHomePageRecommend(url)
        mainPageDao.cacheHomePageRecommend(response)
        response
    }

    private suspend fun requestDaily(url: String) = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchDaily(url)
        mainPageDao.cacheDaily(response)
        response
    }

    private suspend fun requestCommunityRecommend(url: String) = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchCommunityRecommend(url)
        mainPageDao.cacheCommunityRecommend(response)
        response
    }

    private suspend fun requestFollow(url: String) = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchFollow(url)
        mainPageDao.cacheFollow(response)
        response
    }

    private suspend fun requestPushMessage(url: String) = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchPushMessage(url)
        mainPageDao.cachePushMessageInfo(response)
        response
    }

    private suspend fun requestHotSearch() = withContext(Dispatchers.IO) {
        val response = eyepetizerNetwork.fetchHotSearch()
        mainPageDao.cacheHotSearch(response)
        response
    }

    companion object {

        private var repository: MainPageRepository? = null

        fun getInstance(dao: MainPageDao, network: EyepetizerNetwork): MainPageRepository {
            if (repository == null) {
                synchronized(MainPageRepository::class.java) {
                    if (repository == null) {
                        repository = MainPageRepository(dao, network)
                    }
                }
            }

            return repository!!
        }
    }
}