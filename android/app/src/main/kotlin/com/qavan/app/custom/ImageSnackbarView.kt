package com.qavan.app.custom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.*
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateMargins
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback
import com.qavan.app.R
import com.qavan.app.extensions.EMPTY
import com.qavan.app.extensions.view.findSuitableParent
import com.qavan.app.extensions.view.onClick
import timber.log.Timber

internal class ImageSnackbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    internal val image: ImageView
    internal val textView: TextView

    init {
        val v = View.inflate(context, R.layout.custom_image_snackbar, this)
        image = v.findViewById(R.id.customImageSnackbarImage)
        textView = v.findViewById(R.id.customImageSnackbarText)
        clipToPadding = false
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(image, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(image, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {

    }

}

class ImageSnackbar private constructor(
    parent: ViewGroup,
    content: ImageSnackbarView
) : BaseTransientBottomBar<ImageSnackbar>(parent, content, content) {

    init {
        view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        view.setPadding(0, 0, 0, 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    companion object {

        const val TOP = Gravity.TOP or Gravity.CENTER
        const val BOTTOM = Gravity.BOTTOM or Gravity.CENTER

        fun make(
            view: View,
            @DrawableRes imageRes: Int = R.drawable.ic_placeholder,
            @ColorRes imageTintRes: Int = 0,
            text: CharSequence = String.EMPTY,
            marginTop: Int? = null,
            marginBottom: Int? = null,
            gravity: Int = BOTTOM,
        ): ImageSnackbar {

            val parent = requireNotNull(view.findSuitableParent()) {
                Timber.e("No suitable parent found from the given view. Please provide a valid view.")
            }

            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.custom_image_snackbar_view,
                parent,
                false
            ) as ImageSnackbarView

            customView.image.setImageResource(imageRes)
            if (imageTintRes != 0) {
                val context = view.context
                val imageTintColor = ResourcesCompat.getColor(context.resources, imageTintRes, context.theme)
                val imageTintList = ColorStateList.valueOf(imageTintColor)
                customView.image.imageTintList = imageTintList

            }

            customView.textView.text = text

            customView.layoutParams = when(val params = customView.layoutParams) {
                is ViewGroup.MarginLayoutParams -> {
                    if (gravity == BOTTOM) {
                        params.updateMargins(bottom = marginBottom ?: params.bottomMargin)
                    }
                    else if (gravity == TOP) {
                        params.updateMargins(top = marginTop ?: params.topMargin)
                    }
                    params
                }
                else -> customView.layoutParams
            }

            return ImageSnackbar(
                parent,
                customView
            ).apply {
                getView().apply {
                    layoutParams = (this.layoutParams as FrameLayout.LayoutParams).apply {
                        this.gravity = gravity
                    }
                    setOnTouchListener { _, event: MotionEvent ->
                        return@setOnTouchListener (customView.parent as ViewGroup).onTouchEvent(event)
                    }
                }
                customView.onClick {
                    this.dismiss()
                }
            }
        }


    }
}