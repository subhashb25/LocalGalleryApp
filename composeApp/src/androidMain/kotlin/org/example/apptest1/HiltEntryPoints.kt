package org.example.apptest1

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.example.apptest1.data.MuseumApi
import org.example.apptest1.data.MuseumStorage
import org.example.apptest1.dbInterface.ItemLocalDataSource


/**
 * 🔹 This interface defines the dependencies that are provided by Hilt
 * and need to be accessed from a non-Hilt context — in this case, Koin.
 *
 * 🔹 It acts as a bridge between Hilt and Koin, exposing the Hilt-provided
 * instances so they can be passed manually into Koin modules.
 *
 * ⚠️ Make sure each method corresponds to a @Provides function
 * or an @Inject-annotated constructor available to Hilt.
 */

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppDependenciesEntryPoint {
    // Provided by Hilt through DatabaseModule
    fun itemLocalDataSource(): ItemLocalDataSource
    // Provided by Hilt or manually through another module
    fun museumApi(): MuseumApi
    // Provided by Hilt or manually through another module
    fun museumStorage(): MuseumStorage
}