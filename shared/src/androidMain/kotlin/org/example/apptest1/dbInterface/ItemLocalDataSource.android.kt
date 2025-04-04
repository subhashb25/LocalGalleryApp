@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.apptest1.dbInterface

import org.example.apptest1.dao.ItemDao
import org.example.apptest1.data.MuseumObject
import org.example.apptest1.db.toDomain
import org.example.apptest1.db.toEntity
import javax.inject.Inject

actual class ItemLocalDataSource @Inject constructor(private val itemDao: ItemDao)  {
    actual suspend fun getAllItems(): List<MuseumObject> {
        return itemDao.getAllItems().map { it.toDomain() }
    }

    actual suspend fun insertItems(items: List<MuseumObject>) {
        itemDao.insertItems(items.map { it.toEntity() })
    }
}