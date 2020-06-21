package com.eyepetizer.android.ui.common.callback

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * 处理gson解析时类型不匹配或者空值问题。https://www.jianshu.com/p/d8dcc656a06e。
 *
 * @author vipyinzhiwei
 * @since  2020/5/30
 */
class GsonTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        val adapter = gson?.getDelegateAdapter(this, type)
        return object : TypeAdapter<T>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter?, value: T) {
                adapter?.write(out, value)
            }

            @Throws(IOException::class)
            override fun read(jr: JsonReader): T? {
                //gson 库会通过JsonReader对json对象的每个字段进项读取,当发现类型不匹配时抛出异常
                return try {
                    adapter?.read(jr)
                } catch (e: Throwable) {
                    //那么我们就在它抛出异常的时候进行处理,让它继续不中断接着往下读取其他的字段就好了
                    consumeAll(jr)
                    null
                }
            }

            @Throws(IOException::class)
            private fun consumeAll(jr: JsonReader) {
                if (jr.hasNext()) {
                    val peek: JsonToken = jr.peek()
                    when {
                        peek === JsonToken.STRING -> {
                            jr.nextString()
                        }
                        peek === JsonToken.BEGIN_ARRAY -> {
                            jr.beginArray()
                            consumeAll(jr)
                            jr.endArray()
                        }
                        peek === JsonToken.BEGIN_OBJECT -> {
                            jr.beginObject()
                            consumeAll(jr)
                            jr.endObject()
                        }
                        peek === JsonToken.END_ARRAY -> {
                            jr.endArray()
                        }
                        peek === JsonToken.END_OBJECT -> {
                            jr.endObject()
                        }
                        peek === JsonToken.NUMBER -> {
                            jr.nextString()
                        }
                        peek === JsonToken.BOOLEAN -> {
                            jr.nextBoolean()
                        }
                        peek === JsonToken.NAME -> {
                            jr.nextName()
                            consumeAll(jr)
                        }
                        peek === JsonToken.NULL -> {
                            jr.nextNull()
                        }
                    }
                }
            }
        }
    }
}