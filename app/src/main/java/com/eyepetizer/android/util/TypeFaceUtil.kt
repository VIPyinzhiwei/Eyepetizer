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

import android.graphics.Typeface
import com.eyepetizer.android.EyepetizerApplication

/**
 * 自定义字体工具类。
 *
 * @author vipyinzhiwei
 * @since  2020/5/24
 */
object TypeFaceUtil {

    const val FZLL_TYPEFACE = 1

    const val FZDB1_TYPEFACE = 2

    const val FUTURA_TYPEFACE = 3

    const val DIN_TYPEFACE = 4

    const val LOBSTER_TYPEFACE = 5

    val fzlLTypeface by lazy {
        Typeface.createFromAsset(EyepetizerApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    val fzdb1Typeface by lazy {
        Typeface.createFromAsset(EyepetizerApplication.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    val futuraTypeface by lazy {
        Typeface.createFromAsset(EyepetizerApplication.context.assets, "fonts/Futura-CondensedMedium.ttf")
    }

    val dinTypeface by lazy {
        Typeface.createFromAsset(EyepetizerApplication.context.assets, "fonts/DIN-Condensed-Bold.ttf")
    }

    val lobsterTypeface by lazy {
        Typeface.createFromAsset(EyepetizerApplication.context.assets, "fonts/Lobster-1.4.otf")
    }
}