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

package com.eyepetizer.android.ui.home.commend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eyepetizer.android.logic.MainPageRepository
import com.eyepetizer.android.logic.model.HomePageRecommend
import kotlinx.coroutines.flow.Flow

class CommendViewModel(val repository: MainPageRepository) : ViewModel() {

    var dataList = ArrayList<HomePageRecommend.Item>()

    fun getPagingData(): Flow<PagingData<HomePageRecommend.Item>> {
        return repository.getHomePageRecommendPagingData().cachedIn(viewModelScope)
    }
}
