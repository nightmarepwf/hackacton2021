package com.qavan.app.extensions.android

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.qavan.app.BuildConfig


inline fun <reified T: Activity> Activity.launchActivityAnim(
    enterAnim: Int,
    exitAnim: Int,
    intent: Intent.() -> Unit = {},
    then: () -> Unit = {
        finishAffinity()
    },
) {
    startActivity(Intent(this, T::class.java).apply {
        intent.invoke(this)
    })
    overridePendingTransition(enterAnim, exitAnim)
    then()
}

inline fun <reified T: Activity> Fragment.launchActivity(
    intent: Intent.() -> Unit = {},
    then: () -> Unit = {
        requireActivity().finishAffinity()
    },
) {
    startActivity(Intent(requireContext(), T::class.java).apply {
        intent.invoke(this)
    })
    then()
}

inline fun <reified T: Activity> Activity.launchActivity(
    intent: Intent.() -> Unit = {},
    then: () -> Unit = {
        finishAffinity()
    },
) {
    startActivity(Intent(this, T::class.java).apply {
        intent.invoke(this)
    })
    then()
}

fun Activity.isPermissionGranted(
    permission: String
): Boolean {
    return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.hideKeyboard(code: Int = 0) {
    (this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.currentFocus?.windowToken,code)
}

inline val Activity.isLocationPermissionsGranted: Boolean
    get() = isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) &&
        isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)

fun Activity.shareText(
    message: String,
    link: String? = null,
    title: String = "Choose one",
) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, BuildConfig.APPLICATION_ID)
    val text = if (link != null)
        "${message}\n${link}"
    else
        message
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(shareIntent, title))
}

fun Activity.shareText(
    text: String,
    title: String = "Choose one",
) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, BuildConfig.APPLICATION_ID)
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(shareIntent, title))
}