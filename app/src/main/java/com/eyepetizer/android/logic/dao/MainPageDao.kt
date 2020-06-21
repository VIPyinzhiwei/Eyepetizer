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

package com.eyepetizer.android.logic.dao

import com.eyepetizer.android.logic.model.*

/**
 * 主页界面（主要包含：首页，社区，通知，我的），对应的Dao操作类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/15
 */
class MainPageDao {

    /*----------------------------首页相关----------------------------*/

    fun cacheDiscovery(bean: Discovery?) {
        //TODO("存储数据到本地")
    }

    fun getCachedDiscovery(): Discovery? {
        TODO("获取本地存储的数据")
    }

    fun cacheHomePageRecommend(bean: HomePageRecommend?) {
        //TODO("存储数据到本地")
    }

    fun getCachedHomePageRecommend(): HomePageRecommend? {
        TODO("获取本地存储的数据")
    }

    fun cacheDaily(bean: Daily?) {
        //TODO("存储数据到本地")
    }

    fun getCachedDaily(): Daily? {
        TODO("获取本地存储的数据")
    }

    /*----------------------------社区相关----------------------------*/

    fun cacheCommunityRecommend(bean: CommunityRecommend?) {
        //TODO("存储数据到本地")
    }

    fun getCachedCommunityRecommend(): CommunityRecommend? {
        TODO("获取本地存储的数据")
    }

    fun cacheFollow(bean: Follow?) {
        //TODO("存储数据到本地")
    }

    fun getCachedFollow(): Follow? {
        TODO("获取本地存储的数据")
    }

    /*----------------------------通知相关----------------------------*/

    fun cachePushMessageInfo(bean: PushMessage?) {
        //TODO("存储数据到本地")
    }

    fun getCachedPushMessageInfo(): PushMessage? {
        TODO("获取本地存储的数据")
    }

    /*----------------------------搜索相关----------------------------*/

    fun cacheHotSearch(bean: List<String>?) {
        //TODO("存储数据到本地")
    }

    fun getHotSearch(): List<String>? {
        TODO("获取本地存储的数据")
    }

}