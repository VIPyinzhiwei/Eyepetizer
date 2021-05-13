package com.eyepetizer.android.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.eyepetizer.android.EyepetizerApplication

/**
 * 获取DataStore实例
 *
 * @author vipyinzhiwei
 * @since  2021/5/12
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = EyepetizerApplication.context.packageName + "_preferences",
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, EyepetizerApplication.context.packageName + "_preferences"))
    })