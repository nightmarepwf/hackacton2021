package com.qavan.app.data.database.converters

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class UriConverter: BaseConverter() {

    @TypeConverter
    fun stringToUri(string: String): Uri {
        return Uri.parse(string)
    }

    @TypeConverter
    fun uriToString(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun stringToUriList(string: String): List<Uri> {
        return string.split(Item).map { Uri.parse(it) }
    }

    @TypeConverter
    fun uriListToString(uriList: List<Uri>): String {
        return uriList.joinToString(separator = Item) {
            it.toString()
        }
    }

    @TypeConverter
    fun stringToUriArrayList(string: String): ArrayList<Uri> {
        val al = ArrayList<Uri>()
        string.split(Item).forEach { al.add(Uri.parse(it)) }
        return al
    }

    @TypeConverter
    fun uriArrayListToString(uriList: ArrayList<Uri>): String {
        return uriList.joinToString(separator = Item) {
            it.toString()
        }
    }

}