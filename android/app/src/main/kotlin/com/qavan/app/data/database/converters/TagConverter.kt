package com.qavan.app.data.database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.qavan.app.data.model.Tag

@ProvidedTypeConverter
class TagConverter: BaseConverter() {

    @TypeConverter
    fun stringToTag(string: String): Tag {
        val r = string.split(Field)
        return Tag(
            id = r[0].toInt(),
            name = r[1],
            color = r[2].toULong(),
        )
    }

    @TypeConverter
    fun tagToString(tag: Tag): String {
        return buildString {
            append(tag.id)
            append(Field)
            append(tag.name)
            append(Field)
            append(tag.color)
        }
    }

    @TypeConverter
    fun stringToTags(string: String): ArrayList<Tag> {
        return ArrayList(string.split(Item).map { stringToTag(it) })
    }

    @TypeConverter
    fun tagsToStrings(tag: ArrayList<Tag>): String {
        return tag.joinToString(separator = Item) { tagToString(it) }
    }

}