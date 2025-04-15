package org.example.apptest1.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.apptest1.data.MuseumObject
import org.example.apptest1.db.ItemEntity
import javax.inject.Singleton

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Query("SELECT * FROM items WHERE objectID = :id LIMIT 1")
    fun getItemById(id: Int): Flow<ItemEntity?>
}