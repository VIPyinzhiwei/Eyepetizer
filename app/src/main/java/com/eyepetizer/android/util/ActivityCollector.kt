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

package com.eyepetizer.android.util

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * 管理应用程序中所有Activity。
 *
 * @author vipyinzhiwei
 * @since  2020/4/29
 */
object ActivityCollector {

    private val activitys = Stack<WeakReference<Activity>>()

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    fun pushTask(task: WeakReference<Activity>?) {
        activitys.push(task)
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    fun removeTask(task: WeakReference<Activity>?) {
        activitys.remove(task)
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    fun removeTask(taskIndex: Int) {
        if (activitys.size > taskIndex) activitys.removeAt(taskIndex)
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    fun removeToTop() {
        val end = activitys.size
        val start = 1
        for (i in end - 1 downTo start) {
            val mActivity = activitys[i].get()
            if (null != mActivity && !mActivity.isFinishing) {
                mActivity.finish()
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    fun removeAll() {
        for (task in activitys) {
            val mActivity = task.get()
            if (null != mActivity && !mActivity.isFinishing) {
                mActivity.finish()
            }
        }
    }

}
