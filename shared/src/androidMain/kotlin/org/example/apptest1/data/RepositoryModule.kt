package org.example.apptest1.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.example.apptest1.dbInterface.ItemLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMuseumRepository(
        museumApi: MuseumApi,
        museumStorage: MuseumStorage,
        localDataSource: ItemLocalDataSource
    ): MuseumRepository {
        return MuseumRepository(museumApi, museumStorage, localDataSource) // ✅ Manually created
    }
}
//This ensures that MuseumRepository is provided correctly via Hilt in Android.