package com.qavan.app.extensions.android

import androidx.activity.result.ActivityResultLauncher
import com.qavan.app.data.constants.MIME_TYPE_IMAGES
import com.qavan.app.data.constants.MIME_TYPE_VIDEOS

fun ActivityResultLauncher<String>.launchImagePicker() {
    this.launch(MIME_TYPE_IMAGES)
}

fun ActivityResultLauncher<String>.launchVideoPicker() {
    this.launch(MIME_TYPE_VIDEOS)
}