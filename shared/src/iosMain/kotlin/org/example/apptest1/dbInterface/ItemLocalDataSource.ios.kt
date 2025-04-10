package org.example.apptest1.dbInterface

import org.example.apptest1.data.MuseumObject

actual class ItemLocalDataSource () {
    private val items = mutableListOf<MuseumObject>()

    actual suspend fun insertItems(items: List<MuseumObject>) {
        this.items.addAll(items)
    }

    actual suspend fun getAllItems(): List<MuseumObject> = items
}