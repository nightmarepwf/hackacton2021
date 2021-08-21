package com.qavan.app.manager

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

interface ResourceManager {
    @ColorInt fun color(@ColorRes colorResInt: Int): Int
    fun drawable(@ColorRes drawableResInt: Int): Drawable
    fun string(@StringRes stringResInt: Int): String
}

class ResourceManagerImpl(
    private val context: Context,
): ResourceManager {

    private val resources get() = context.resources
    private val theme get() = context.theme

    override fun color(colorResInt: Int) = ResourcesCompat.getColor(resources, colorResInt, theme)

    override fun drawable(drawableResInt: Int) = checkNotNull(ResourcesCompat.getDrawable(resources, drawableResInt, theme))

    override fun string(stringResInt: Int): String  = resources.getString(stringResInt)
}