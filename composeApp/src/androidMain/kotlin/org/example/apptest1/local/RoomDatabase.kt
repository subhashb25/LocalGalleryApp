package org.example.apptest1.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.apptest1.dao.ItemDao
import org.example.apptest1.db.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}