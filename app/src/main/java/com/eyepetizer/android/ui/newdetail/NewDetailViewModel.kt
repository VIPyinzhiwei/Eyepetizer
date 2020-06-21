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

package com.eyepetizer.android.ui.newdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.eyepetizer.android.logic.VideoRepository
import com.eyepetizer.android.logic.model.VideoDetail
import com.eyepetizer.android.logic.model.VideoRelated
import com.eyepetizer.android.logic.model.VideoReplies
import com.eyepetizer.android.logic.network.api.VideoService

/**
 * 视频详情界面绑定的ViewModel。
 *
 * @author vipyinzhiwei
 * @since  2020/5/14
 */
class NewDetailViewModel(repository: VideoRepository) : ViewModel() {

    var relatedDataList = ArrayList<VideoRelated.Item>()

    var repliesDataList = ArrayList<VideoReplies.Item>()

    var videoInfoData: NewDetailActivity.VideoInfo? = null

    var videoId: Long = 0L

    private var repliesLiveData_ = MutableLiveData<String>()
    private var videoDetailLiveData_ = MutableLiveData<RequestParam>()
    private var repliesAndRepliesLiveData_ = MutableLiveData<RequestParam>()

    var nextPageUrl: String? = null

    val videoDetailLiveData = Transformations.switchMap(videoDetailLiveData_) {
        liveData {
            val resutlt = try {
                val videoDetail = repository.refreshVideoDetail(it.videoId, it.repliesUrl)   //视频信息+相关推荐+评论
                Result.success(videoDetail)
            } catch (e: Exception) {
                Result.failure<VideoDetail>(e)
            }
            emit(resutlt)
        }
    }

    val repliesAndRepliesLiveData = Transformations.switchMap(repliesAndRepliesLiveData_) {
        liveData {
            val resutlt = try {
                val videoDetail = repository.refreshVideoRelatedAndVideoReplies(it.videoId, it.repliesUrl)   //相关推荐+评论
                Result.success(videoDetail)
            } catch (e: Exception) {
                Result.failure<VideoDetail>(e)
            }
            emit(resutlt)
        }
    }

    val repliesLiveData = Transformations.switchMap(repliesLiveData_) {
        liveData {
            val resutlt = try {
                val videoDetail = repository.refreshVideoReplies(it)   //评论
                Result.success(videoDetail)
            } catch (e: Exception) {
                Result.failure<VideoReplies>(e)
            }
            emit(resutlt)
        }
    }

    fun onRefresh() {
        if (videoInfoData == null) {
            videoDetailLiveData_.value = RequestParam(videoId, "${VideoService.VIDEO_REPLIES_URL}$videoId")
        } else {
            repliesAndRepliesLiveData_.value = RequestParam(videoInfoData?.videoId ?: 0L, "${VideoService.VIDEO_REPLIES_URL}${videoInfoData?.videoId ?: 0L}")
        }
    }

    fun onLoadMore() {
        repliesLiveData_.value = nextPageUrl ?: ""
    }

    inner class RequestParam(val videoId: Long, val repliesUrl: String)
}