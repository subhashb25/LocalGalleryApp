package org.example.apptest1.dbInterface

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.apptest1.data.MuseumObject
import org.example.apptest1.db.AppDatabase
import org.example.apptest1.db.MuseumObjectEntity

//MuseumDatabase
actual class ItemLocalDataSource(
    private val db: AppDatabase
) {

    private val queries = db.museumObjectQueries

    actual suspend fun insertItems(items: List<MuseumObject>) {
        items.forEach { item ->
            queries.insertObject(
                objectID = item.objectID.toLong(),
                title = item.title,
                artistDisplayName = item.artistDisplayName,
                medium = item.medium,
                dimensions = item.dimensions,
                objectURL = item.objectURL,
                objectDate = item.objectDate,
                primaryImage = item.primaryImage,
                primaryImageSmall = item.primaryImageSmall,
                repository = item.repository,
                department = item.department,
                creditLine = item.creditLine
            )
        }
    }

    actual suspend fun getAllItems(): List<MuseumObject> {
        return queries.selectAll().executeAsList().map {
            MuseumObject(
                objectID = it.objectID.toInt(),
                title = it.title.orEmpty(),
                artistDisplayName = it.artistDisplayName.orEmpty(),
                medium = it.medium.orEmpty(),
                dimensions = it.dimensions.orEmpty(),
                objectURL = it.objectURL.orEmpty(),
                objectDate = it.objectDate.orEmpty(),
                primaryImage = it.primaryImage.orEmpty(),
                primaryImageSmall = it.primaryImageSmall.orEmpty(),
                repository = it.repository.orEmpty(),
                department = it.department.orEmpty(),
                creditLine = it.creditLine.orEmpty()
            )
        }
    }

    actual suspend fun getItemById(id: Int): Flow<MuseumObject?> {
        return queries.getObjectById(id.toLong())
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toMuseumObject() }
    }

    private fun MuseumObjectEntity.toMuseumObject(): MuseumObject {
        return MuseumObject(
            objectID = objectID.toInt(),
            title = title.orEmpty(),
            artistDisplayName = artistDisplayName.orEmpty(),
            medium = medium.orEmpty(),
            dimensions = dimensions.orEmpty(),
            objectURL = objectURL.orEmpty(),
            objectDate = objectDate.orEmpty(),
            primaryImage = primaryImage.orEmpty(),
            primaryImageSmall = primaryImageSmall.orEmpty(),
            repository = repository.orEmpty(),
            department = department.orEmpty(),
            creditLine = creditLine.orEmpty()
        )
    }
}
