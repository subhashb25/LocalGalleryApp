package org.example.apptest1.dbInterface

import org.example.apptest1.data.MuseumObject

actual class ItemLocalDataSource {
    actual suspend fun getAllItems(): List<MuseumObject> {
        TODO("Not yet implemented")
    }

    actual suspend fun insertItems(items: List<MuseumObject>) {
    }
}