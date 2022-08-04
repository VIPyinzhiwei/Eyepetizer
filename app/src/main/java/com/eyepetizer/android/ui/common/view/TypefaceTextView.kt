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

package com.eyepetizer.android.ui.common.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.eyepetizer.android.R
import com.eyepetizer.android.util.TypeFaceUtil

/**
 * 带有自定义字体TextView。
 *
 * @author vipyinzhiwei
 * @since  2020/5/24
 */
class TypefaceTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.TypefaceTextView, 0, 0)
            val typefaceType = typedArray.getInt(R.styleable.TypefaceTextView_typeface, 0)
            typeface = getTypeface(typefaceType)
            typedArray.recycle()
        }
    }

    companion object {

        /**
         * 根据字体类型，获取自定义字体。
         */
        fun getTypeface(typefaceType: Int?) = when (typefaceType) {
            TypeFaceUtil.FZLL_TYPEFACE -> TypeFaceUtil.fzlLTypeface
            TypeFaceUtil.FZDB1_TYPEFACE -> TypeFaceUtil.fzdb1Typeface
            TypeFaceUtil.FUTURA_TYPEFACE -> TypeFaceUtil.futuraTypeface
            TypeFaceUtil.DIN_TYPEFACE -> TypeFaceUtil.dinTypeface
            TypeFaceUtil.LOBSTER_TYPEFACE -> TypeFaceUtil.lobsterTypeface
            else -> Typeface.DEFAULT
        }
    }
}