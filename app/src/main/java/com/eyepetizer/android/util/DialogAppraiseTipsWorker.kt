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

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.dp2px
import com.eyepetizer.android.extension.screenWidth
import com.eyepetizer.android.ui.common.ui.WebViewActivity
import com.umeng.analytics.MobclickAgent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * WorkManager组件，执行弹出框后台任务。
 *
 * @author vipyinzhiwei
 * @since  2020/5/21
 */
class DialogAppraiseTipsWorker(val context: Context, parms: WorkerParameters) : Worker(context, parms) {

    override fun doWork(): Result {
        return if (isNeedShowDialog) {
            Result.retry()
        } else {
            Result.success()
        }
    }

    companion object {
        const val TAG = "DialogAppraiseTipsWorker"

        val showDialogWorkRequest = OneTimeWorkRequest.Builder(DialogAppraiseTipsWorker::class.java)
            .addTag(TAG)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS)
            .build()

        /**
         * 是否需要弹出对话框
         */
        var isNeedShowDialog: Boolean
            get() = DataStoreUtils.readBooleanData("is_need_show_dialog", true)
            set(value) {
                CoroutineScope(Dispatchers.IO).launch { DataStoreUtils.saveBooleanData("is_need_show_dialog", value) }
            }

        private var dialog: AlertDialog? = null

        fun showDialog(context: Context) {
            if (!isNeedShowDialog) return

            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_appraise_tips, null).apply {
                findViewById<TextView>(R.id.tvEncourageMessage).text = String.format(GlobalUtil.getString(R.string.encourage_message), GlobalUtil.appName)
                findViewById<TextView>(R.id.tvPositiveButton).setOnClickListener {
                    MobclickAgent.onEvent(context, Const.Mobclick.EVENT8)
                    dialog?.dismiss()
                }
                findViewById<TextView>(R.id.tvNegativeButton).setOnClickListener {
                    MobclickAgent.onEvent(context, Const.Mobclick.EVENT9)
                    dialog?.dismiss()
                    WebViewActivity.start(context, WebViewActivity.DEFAULT_TITLE, WebViewActivity.DEFAULT_URL, true, false, WebViewActivity.MODE_SONIC_WITH_OFFLINE_CACHE)
                }
            }
            dialog = AlertDialog.Builder(context, R.style.EyepetizerAlertDialogStyle).setCancelable(false).setView(dialogView).create()
            dialog?.show()
            dialog?.window?.attributes = dialog?.window?.attributes?.apply {
                width = screenWidth - (dp2px(20f) * 2)
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            isNeedShowDialog = false
            MobclickAgent.onEvent(context, Const.Mobclick.EVENT7)
        }
    }
}