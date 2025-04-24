package org.example.apptest1.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.apptest1.data.MuseumObject

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val objectID: Int,
    val title: String,
    val artistDisplayName: String,
    val medium: String,
    val dimensions: String,
    val objectURL: String,
    val objectDate: String,
    val primaryImage: String,
    val primaryImageSmall: String,
    val repository: String,
    val department: String,
    val creditLine: String,
)
//pulling data from db
fun ItemEntity.toDomain(): MuseumObject {
    return MuseumObject(
        objectID = objectID,
        title = title,
        artistDisplayName = artistDisplayName,
        medium = medium,
        dimensions = dimensions,
        objectURL = objectURL,
        objectDate = objectDate,
        primaryImage = primaryImage,
        primaryImageSmall = primaryImageSmall,
        repository = repository,
        department = department,
        creditLine = creditLine
    )
}
//pushing data to db
fun MuseumObject.toEntity(): ItemEntity {
    return ItemEntity(
        objectID = objectID,
        title = title,
        artistDisplayName = artistDisplayName,
        medium = medium,
        dimensions = dimensions,
        objectURL = objectURL,
        objectDate = objectDate,
        primaryImage = primaryImage,
        primaryImageSmall = primaryImageSmall,
        repository = repository,
        department = department,
        creditLine = creditLine
    )
}