package com.qavan.app.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Tag(
    @ColumnInfo(name = "tagId") val id: Int,
    @ColumnInfo(name = "tagName") val name: String = "Tag name",
    @ColumnInfo(name = "tagColor") val color: ULong = 0UL,
    @Ignore val paddingStart: Int = 0,
): Parcelable
