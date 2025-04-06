package org.example.apptest1.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.example.apptest1.dao.ItemDao
import org.example.apptest1.dbInterface.ItemLocalDataSource

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    fun provideItemLocalDataSource(itemDao: ItemDao): ItemLocalDataSource {
        return ItemLocalDataSource(itemDao)
    }
}