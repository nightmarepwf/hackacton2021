package com.qavan.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qavan.app.data.database.converters.TagConverter
import com.qavan.app.data.database.converters.UriConverter
import com.qavan.app.data.database.dao.ItemsDao
import com.qavan.app.data.model.Item

@Database(
    entities = [
        Item::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        UriConverter::class,
        TagConverter::class,
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
}