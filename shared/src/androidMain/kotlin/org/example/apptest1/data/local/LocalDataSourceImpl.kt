package org.example.apptest1.data.local

import org.example.apptest1.dao.ItemDao
import org.example.apptest1.db.ItemEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val itemDao: ItemDao) {
    suspend fun getAllItems(): List<ItemEntity> = itemDao.getAllItems()
    suspend fun insertItems(items: List<ItemEntity>) = itemDao.insertItems(items)
}