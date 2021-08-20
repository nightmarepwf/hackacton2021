package com.qavan.app.manager

import android.text.SpannableString
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.qavan.app.R
import com.qavan.app.custom.ImageSnackbar
import timber.log.Timber
import java.lang.ref.WeakReference

class ImageSnackbarManager(
    private val resourceManager: ResourceManager,
) {
    private val queue: ArrayList<ImageSnackbarPayload> by lazy { ArrayList() }

    private var viewGroup: WeakReference<ViewGroup> = WeakReference(null)
    private var currentSnackbar: WeakReference<ImageSnackbar> = WeakReference(null)

    fun attachToView(viewGroup: ViewGroup) {
        this.viewGroup = WeakReference(viewGroup)
    }

    private fun showSnackbar(
        data: ImageSnackbarPayload,
    ) {
        currentSnackbar = WeakReference(
            ImageSnackbar.make(
                requireNotNull(viewGroup.get()) {
                    Timber.e("Not attached to ViewGroup!")
                },
                imageRes = data.image,
                imageTintRes = data.imageTint,
                text = data.text,
                marginTop = data.marginTop,
                marginBottom = data.marginBottom,
                gravity = data.gravity,
            ).addCallback(object : BaseTransientBottomBar.BaseCallback<ImageSnackbar>() {
                override fun onDismissed(transientBottomBar: ImageSnackbar?, event: Int) {
                    currentSnackbar = WeakReference(null)
                    if (queue.size > 0) {
                        queue.remove(data)
                        queue.firstOrNull()?.let { showSnackbar(it) }
                    }
                }
            })
        )
        currentSnackbar.get()?.show()
    }

    private fun show(
        data: ImageSnackbarPayload,
    ) {
        queue.add(data)
        if (currentSnackbar.get() == null) {
            showSnackbar(data)
        }
    }

    private fun showNext(
        data: ImageSnackbarPayload,
    ) {
        if (currentSnackbar.get() == null) {
            showSnackbar(data)
        }
        else {
            queue.add(0, data)
            currentSnackbar.get()?.dismiss()
        }
    }

    fun show(
        @DrawableRes image: Int = R.drawable.ic_placeholder,
        @ColorRes imageTint: Int = 0,
        text: CharSequence,
        marginTop: Int? = null,
        marginBottom: Int? = null,
        priority: Int = PRIORITY_DEFAULT,
        gravity: Int = ImageSnackbar.BOTTOM,
    ) {
        when(priority) {
            PRIORITY_DEFAULT -> {
                show(
                    ImageSnackbarPayload(
                        image = image,
                        imageTint = imageTint,
                        text = text,
                        marginTop = marginTop,
                        marginBottom = marginBottom,
                        gravity = gravity,
                    )
                )
            }
            PRIORITY_HIGH -> {
                showNext(
                    ImageSnackbarPayload(
                        image = image,
                        imageTint = imageTint,
                        text = text,
                        marginTop = marginTop,
                        marginBottom = marginBottom,
                        gravity = gravity,
                    )
                )
            }
        }
    }

    fun show(
        @DrawableRes image: Int = R.drawable.ic_placeholder,
        @ColorRes imageTint: Int = 0,
        @StringRes text: Int,
        marginTop: Int? = null,
        marginBottom: Int? = null,
        priority: Int = PRIORITY_DEFAULT,
        gravity: Int = ImageSnackbar.BOTTOM,
    ) {
        when(priority) {
            PRIORITY_DEFAULT -> {
                show(
                    ImageSnackbarPayload(
                        image = image,
                        imageTint = imageTint,
                        text = SpannableString(resourceManager.string(text)),
                        marginTop = marginTop,
                        marginBottom = marginBottom,
                        gravity = gravity,
                    )
                )
            }
            PRIORITY_HIGH -> {
                showNext(
                    ImageSnackbarPayload(
                        image = image,
                        imageTint = imageTint,
                        text = SpannableString(resourceManager.string(text)),
                        marginTop = marginTop,
                        marginBottom = marginBottom,
                        gravity = gravity,
                    )
                )
            }
        }
    }

    fun clear() {
        queue.clear()
        currentSnackbar.get()?.dismiss()
    }

    companion object {
        const val PRIORITY_DEFAULT = 0
        const val PRIORITY_HIGH = 1
    }
}

internal data class ImageSnackbarPayload(
    @DrawableRes val image: Int = R.drawable.ic_placeholder,
    @ColorRes val imageTint: Int = 0,
    val text: CharSequence,
    val marginTop: Int? = null,
    @DimenRes val marginBottom: Int? = R.dimen.margin_bottom_nav_button,
    val gravity: Int = ImageSnackbar.BOTTOM,
)