package com.qavan.app.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.qavan.app.extensions.EMPTY
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table-items")
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = String.EMPTY,
    var description: String = String.EMPTY,
    var images: ArrayList<Uri> = ArrayList(),
    var tags: ArrayList<Tag> = ArrayList(),
    @Ignore var isDeleted: Boolean = false,
    @Ignore var composeScrollValue: Int = 0,
) : Parcelable