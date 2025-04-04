package org.example.apptest1.dbInterface

import org.example.apptest1.data.MuseumObject


expect class ItemLocalDataSource {
    suspend fun getAllItems(): List<MuseumObject>
    suspend fun insertItems(items: List<MuseumObject>)
}