package com.qavan.app.extensions.android

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(requireActivity(), permission) == PackageManager.PERMISSION_GRANTED
}

inline fun <reified T: Activity> Fragment.launchActivityAnim(
    enterAnim: Int,
    exitAnim: Int,
    intent: Intent.() -> Unit = {},
    then: () -> Unit = {
        requireActivity().finishAffinity()
    },
) {
    startActivity(Intent(requireContext(), T::class.java).apply {
        intent.invoke(this)
    })
    requireActivity().overridePendingTransition(enterAnim, exitAnim)
    then()
}

inline fun Fragment.launchWhenResume(
    crossinline action: suspend () -> Unit
) {
    lifecycleScope.launchWhenResumed {
        action()
    }
}