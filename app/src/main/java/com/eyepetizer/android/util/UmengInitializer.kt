package com.eyepetizer.android.util

import android.content.Context
import androidx.startup.Initializer
import com.eyepetizer.android.BuildConfig
import com.umeng.commonsdk.UMConfigure

/**
 * App Startup
 *
 * @author vipyinzhiwei
 * @since  2021/5/12
 */
class UmengInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, null)
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

}