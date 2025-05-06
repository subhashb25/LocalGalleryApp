package org.example.apptest1.dbInterface

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.example.apptest1.data.MuseumObject
import org.example.apptest1.db.AppDatabase

actual class ItemLocalDataSource (
    private val db: AppDatabase
) {
    private val items = mutableListOf<MuseumObject>()
    actual suspend fun insertItems(items: List<MuseumObject>) {
        this.items.addAll(items)
    }

    actual suspend fun getAllItems(): List<MuseumObject> = items
    actual suspend fun getItemById(id: Int): Flow<MuseumObject?> {
        val dummy = MuseumObject(
            objectID = id,
            title = "Dummy Title",
            artistDisplayName = "Dummy Artist",
            medium = "Dummy Medium",
            dimensions = "10 x 10 cm",
            objectURL = "http://dummyurl.com",
            objectDate = "1900",
            primaryImage = "http://dummyimage.com/primary",
            primaryImageSmall = "http://dummyimage.com/small",
            repository = "Dummy Repository",
            department = "Dummy Department",
            creditLine = "Dummy Credit Line"
        )
        return flowOf(dummy)
    }
}