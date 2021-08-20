package com.qavan.app.extensions

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.*

inline fun fetchAttrs(
    context: Context,
    attrs: IntArray,
    set: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    fetch: TypedArray .() -> Unit = {}
) {
    val typedArray = context.theme.obtainStyledAttributes(
        set,
        attrs,
        defStyleAttr,
        defStyleRes
    )
    with(typedArray) {
        try {
            fetch()
        } finally {
            recycle()
        }
    }
}
@ColorInt
inline fun TypedArray.getColorOrDefault(
    @StyleableRes index: Int,
    default: () -> Int
): Int = getColor(index, default())

@Px
inline fun TypedArray.getTextSizeOrDefault(
    @StyleableRes index: Int,
    default: () -> Int
): Int = getDimensionPixelSize(index, default())

@Px
inline fun TypedArray.getTextColorOrDefault(
    @StyleableRes index: Int,
    default: () -> Int
): Int = getColor(index, default())

inline fun TypedArray.getStringOrDefault(
    @StyleableRes index: Int,
    default: () -> String = { String.EMPTY }
): String = getString(index) ?: default()
