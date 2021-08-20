package com.qavan.app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.qavan.app.data.model.Item

@Dao
interface ItemsDao {

    // adds items and returns their unique ids (primary keys)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(vararg item: Item): List<Long>

    // adds items and returns their unique ids (primary keys)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items: List<Item>): List<Long>

}