package com.qavan.app.extensions.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnWindowFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.qavan.app.R
import com.qavan.app.extensions.EMPTY
import com.qavan.app.extensions.kotlin.launchOnUI
import kotlinx.coroutines.delay

inline val View.visible: Unit
    get() {
        visibility = View.VISIBLE
    }

inline val View.gone: Unit
    get() {
        visibility = View.GONE
    }

inline fun View.onClick(
    crossinline action: (View) -> Unit = {},
) {
    setOnClickListener {
        action(this)
    }
}

// https://developer.squareup.com/blog/showing-the-android-keyboard-reliably/
fun View.focusAndShowKeyboard(
    setCursorAtEnd: Boolean = false,
) {
    /**
     * This is to be called when the window already has focus.
     */
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                if (setCursorAtEnd) {
                    when (this is EditText) {
                        true -> {
                            launchOnUI {
                                delay(350)
                                selectAll()
                            }
                        }
                    }
                }
            }
        }
    }

    requestFocus()
    if (hasWindowFocus()) {
        // No need to wait for the window to get focus.
        showTheKeyboardNow()
    } else {
        // We need to wait until the window gets focus.
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    // This notification will arrive just before the InputMethodManager gets set up.
                    if (hasFocus) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        // It’s very important to remove this listener once we are done.
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
}

fun View.updateTopPadding(
    padding: Int,
) {
    setPadding(
        paddingStart,
        padding,
        paddingRight,
        paddingBottom,
    )
}

fun View.aboveBottomNavBar() {
    setPadding(
        paddingStart,
        paddingTop,
        paddingRight,
        context.resources.getDimension(R.dimen.margin_bottom_nav_button).toInt(),
    )
}

fun View.aboveBottomNavBarBigger(multiplier: Int = 1) {
    setPadding(
        paddingStart,
        paddingTop,
        paddingRight,
        context.resources.getDimension(R.dimen.margin_bottom_nav).toInt() * multiplier,
    )
}

fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}

inline var View.nameOfTransition: String
    get() = ViewCompat.getTransitionName(this) ?: String.EMPTY
    set(value) {
        ViewCompat.setTransitionName(this, value)
    }