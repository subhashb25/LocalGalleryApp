package org.example.apptest1.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.example.apptest1.dao.ItemDao
import org.example.apptest1.data.local.AppDatabase
import org.example.apptest1.data.local.LocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideItemDao(database: AppDatabase): ItemDao {
        return database.itemDao()
    }

    @Provides
    fun provideLocalDataSource(itemDao: ItemDao): LocalDataSource {
        return LocalDataSource(itemDao)
    }
}