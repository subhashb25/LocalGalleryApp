
package org.example.apptest1.dbInterface

import org.example.apptest1.dao.ItemDao
import org.example.apptest1.data.MuseumObject
import org.example.apptest1.db.toDomain
import org.example.apptest1.db.toEntity
import javax.inject.Singleton

// The "actual" class is the Android-specific implementation of the expected API.
// We mark this class as @Singleton to scope it via Hilt.
@Singleton
actual class ItemLocalDataSource (private val itemDao: ItemDao) {
    actual suspend fun getAllItems(): List<MuseumObject> {
        return itemDao.getAllItems().map { it.toDomain() }
    }

    actual suspend fun insertItems(items: List<MuseumObject>) {
        itemDao.insertItems(items.map { it.toEntity() })
    }
}