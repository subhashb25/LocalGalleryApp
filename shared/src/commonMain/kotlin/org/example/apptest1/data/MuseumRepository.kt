package org.example.apptest1.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.example.apptest1.dbInterface.ItemLocalDataSource

class MuseumRepository(
    private val museumApi: MuseumApi,
    private val museumStorage: MuseumStorage,
    private val localDataSource: ItemLocalDataSource,
) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        museumStorage.saveObjects(museumApi.getData().also { localDataSource.insertItems(it) })
    }

    //fun getObjects(): Flow<List<MuseumObject>> = museumStorage.getObjects()


    fun getObjects(): Flow<List<MuseumObject>> = flow {
        museumStorage.getObjects().collect { cachedItems ->
            if (cachedItems.isNotEmpty()) {
                emit(cachedItems) // Return from memory if available
            } else {
                val dbItems = localDataSource.getAllItems()
                emit(dbItems) // Return from Room if memory cache is empty
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getObjectById(objectId: Int): Flow<MuseumObject?> = museumStorage.getObjectById(objectId)
}
