package org.example.apptest1.dbInterface

import kotlinx.coroutines.flow.Flow
import org.example.apptest1.data.MuseumObject


expect class ItemLocalDataSource {
    suspend fun getAllItems(): List<MuseumObject>
    suspend fun insertItems(items: List<MuseumObject>)
    suspend fun getItemById(id: Int): Flow<MuseumObject?>
}