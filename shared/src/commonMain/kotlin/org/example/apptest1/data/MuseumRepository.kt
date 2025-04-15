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
        // First, check if local data exists
        val localData = localDataSource.getAllItems()

        if (localData.isEmpty()) {
            // If no local data is available, call the API and save the data locally
            val apiData = museumApi.getData()
            localDataSource.insertItems(apiData)
            museumStorage.saveObjects(apiData)  // Cache the data in memory storage
        } else {
            // Local data is available, no need to call the API
            museumStorage.saveObjects(localData)  // Cache the local data in memory storage
        }
    }


    fun getObjects(): Flow<List<MuseumObject>> = flow {
        museumStorage.getObjects().collect { cachedItems ->
            when {
                cachedItems.isNotEmpty() -> {
                    emit(cachedItems) // Return from memory if available
                }
                else -> {
                    val dbItems = localDataSource.getAllItems()
                    emit(dbItems) // Return from Room if memory cache is empty
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getObjectById(objectId: Int): Flow<MuseumObject?> = flow {
        museumStorage.getObjectById(objectId).collect { cachedObject ->
            when {
                cachedObject != null -> {
                    emit(cachedObject) // Return from memory cache if available
                }
                else -> {
                    // Collect the flow from localDataSource.getItemById() before emitting the value
                    localDataSource.getItemById(objectId).collect { dbObject ->
                        emit(dbObject) // Emit the object from Room database
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}
