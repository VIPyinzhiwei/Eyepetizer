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

import com.eyepetizer.android.logic.model.VideoBeanForClient
import com.eyepetizer.android.logic.model.VideoDetail
import com.eyepetizer.android.logic.model.VideoRelated
import com.eyepetizer.android.logic.model.VideoReplies

/**
 * 视频相关，对应的Dao操作类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/15
 */
class VideoDao {

    fun cacheVideoDetail(bean: VideoDetail?) {
        //TODO("存储数据到本地")
    }

    fun getCachedVideoDetail(): VideoDetail? {
        TODO("获取本地存储的数据")
    }

    fun cacheVideoBeanForClient(bean: VideoBeanForClient?) {
        //TODO("存储数据到本地")
    }

    fun getCachedVideoBeanForClient(): VideoBeanForClient? {
        TODO("获取本地存储的数据")
    }

    fun cacheVideoRelated(bean: VideoRelated?) {
        //TODO("存储数据到本地")
    }

    fun getCachedVideoRelated(): VideoRelated? {
        TODO("获取本地存储的数据")
    }

    fun cacheVideoReplies(bean: VideoReplies?) {
        //TODO("存储数据到本地")
    }

    fun getCachedVideoReplies(): VideoReplies? {
        TODO("获取本地存储的数据")
    }
}