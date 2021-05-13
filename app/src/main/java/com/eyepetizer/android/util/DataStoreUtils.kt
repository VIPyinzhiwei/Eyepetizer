package com.eyepetizer.android.util

import androidx.datastore.preferences.core.*
import com.eyepetizer.android.extension.dataStore
import com.eyepetizer.android.util.DataStoreUtils.clear
import com.eyepetizer.android.util.DataStoreUtils.clearSync
import com.eyepetizer.android.util.DataStoreUtils.getData
import com.eyepetizer.android.util.DataStoreUtils.getSyncData
import com.eyepetizer.android.util.DataStoreUtils.putData
import com.eyepetizer.android.util.DataStoreUtils.putSyncData
import com.eyepetizer.android.util.DataStoreUtils.readBooleanData
import com.eyepetizer.android.util.DataStoreUtils.readBooleanFlow
import com.eyepetizer.android.util.DataStoreUtils.readFloatData
import com.eyepetizer.android.util.DataStoreUtils.readFloatFlow
import com.eyepetizer.android.util.DataStoreUtils.readIntData
import com.eyepetizer.android.util.DataStoreUtils.readIntFlow
import com.eyepetizer.android.util.DataStoreUtils.readLongData
import com.eyepetizer.android.util.DataStoreUtils.readLongFlow
import com.eyepetizer.android.util.DataStoreUtils.readStringData
import com.eyepetizer.android.util.DataStoreUtils.readStringFlow
import com.eyepetizer.android.util.DataStoreUtils.saveBooleanData
import com.eyepetizer.android.util.DataStoreUtils.saveFloatData
import com.eyepetizer.android.util.DataStoreUtils.saveIntData
import com.eyepetizer.android.util.DataStoreUtils.saveLongData
import com.eyepetizer.android.util.DataStoreUtils.saveStringData
import com.eyepetizer.android.util.DataStoreUtils.saveSyncBooleanData
import com.eyepetizer.android.util.DataStoreUtils.saveSyncFloatData
import com.eyepetizer.android.util.DataStoreUtils.saveSyncIntData
import com.eyepetizer.android.util.DataStoreUtils.saveSyncLongData
import com.eyepetizer.android.util.DataStoreUtils.saveSyncStringData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

/**
 * 版权：Zhujiang 个人版权
 *
 * @author zhujiang
 * 创建日期：12/3/20
 *
 * 异步获取数据
 * [getData] [readBooleanFlow] [readFloatFlow] [readIntFlow] [readLongFlow] [readStringFlow]
 * 同步获取数据
 * [getSyncData] [readBooleanData] [readFloatData] [readIntData] [readLongData] [readStringData]
 *
 * 异步写入数据
 * [putData] [saveBooleanData] [saveFloatData] [saveIntData] [saveLongData] [saveStringData]
 * 同步写入数据
 * [putSyncData] [saveSyncBooleanData] [saveSyncFloatData] [saveSyncIntData] [saveSyncLongData] [saveSyncStringData]
 *
 * 异步清除数据
 * [clear]
 * 同步清除数据
 * [clearSync]
 *
 * 描述：DataStore 工具类
 *
 */
object DataStoreUtils {

    @Suppress("UNCHECKED_CAST")
    fun <U> getSyncData(key: String, default: U): U {
        val res = when (default) {
            is Long -> readLongData(key, default)
            is String -> readStringData(key, default)
            is Int -> readIntData(key, default)
            is Boolean -> readBooleanData(key, default)
            is Float -> readFloatData(key, default)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
        return res as U
    }

    @Suppress("UNCHECKED_CAST")
    fun <U> getData(key: String, default: U): Flow<U> {
        val data = when (default) {
            is Long -> readLongFlow(key, default)
            is String -> readStringFlow(key, default)
            is Int -> readIntFlow(key, default)
            is Boolean -> readBooleanFlow(key, default)
            is Float -> readFloatFlow(key, default)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
        return data as Flow<U>
    }

    suspend fun <U> putData(key: String, value: U) {
        when (value) {
            is Long -> saveLongData(key, value)
            is String -> saveStringData(key, value)
            is Int -> saveIntData(key, value)
            is Boolean -> saveBooleanData(key, value)
            is Float -> saveFloatData(key, value)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }

    fun <U> putSyncData(key: String, value: U) {
        when (value) {
            is Long -> saveSyncLongData(key, value)
            is String -> saveSyncStringData(key, value)
            is Int -> saveSyncIntData(key, value)
            is Boolean -> saveSyncBooleanData(key, value)
            is Float -> saveSyncFloatData(key, value)
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }

    fun readBooleanFlow(key: String, default: Boolean = false): Flow<Boolean> =
        dataStore.data.catch {
            //当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
            //但是如果是其他的异常，最好将它抛出去，不要隐藏问题
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[booleanPreferencesKey(key)] ?: default
        }

    fun readBooleanData(key: String, default: Boolean = false): Boolean {
        var value = false
        runBlocking {
            dataStore.data.first {
                value = it[booleanPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun readIntFlow(key: String, default: Int = 0): Flow<Int> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[intPreferencesKey(key)] ?: default
        }

    fun readIntData(key: String, default: Int = 0): Int {
        var value = 0
        runBlocking {
            dataStore.data.first {
                value = it[intPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun readStringFlow(key: String, default: String = ""): Flow<String> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[stringPreferencesKey(key)] ?: default
        }

    fun readStringData(key: String, default: String = ""): String {
        var value = ""
        runBlocking {
            dataStore.data.first {
                value = it[stringPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun readFloatFlow(key: String, default: Float = 0f): Flow<Float> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[floatPreferencesKey(key)] ?: default
        }

    fun readFloatData(key: String, default: Float = 0f): Float {
        var value = 0f
        runBlocking {
            dataStore.data.first {
                value = it[floatPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    fun readLongFlow(key: String, default: Long = 0L): Flow<Long> =
        dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[longPreferencesKey(key)] ?: default
        }

    fun readLongData(key: String, default: Long = 0L): Long {
        var value = 0L
        runBlocking {
            dataStore.data.first {
                value = it[longPreferencesKey(key)] ?: default
                true
            }
        }
        return value
    }

    suspend fun saveBooleanData(key: String, value: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[booleanPreferencesKey(key)] = value
        }
    }

    fun saveSyncBooleanData(key: String, value: Boolean) = runBlocking { saveBooleanData(key, value) }

    suspend fun saveIntData(key: String, value: Int) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[intPreferencesKey(key)] = value
        }
    }

    fun saveSyncIntData(key: String, value: Int) = runBlocking { saveIntData(key, value) }

    suspend fun saveStringData(key: String, value: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[stringPreferencesKey(key)] = value
        }
    }

    fun saveSyncStringData(key: String, value: String) = runBlocking { saveStringData(key, value) }

    suspend fun saveFloatData(key: String, value: Float) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[floatPreferencesKey(key)] = value
        }
    }

    fun saveSyncFloatData(key: String, value: Float) = runBlocking { saveFloatData(key, value) }

    suspend fun saveLongData(key: String, value: Long) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[longPreferencesKey(key)] = value
        }
    }

    fun saveSyncLongData(key: String, value: Long) = runBlocking { saveLongData(key, value) }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    fun clearSync() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }

}