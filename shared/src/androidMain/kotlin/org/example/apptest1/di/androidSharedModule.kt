package org.example.apptest1.di

import org.example.apptest1.data.MuseumApi
import org.example.apptest1.data.MuseumStorage
import org.example.apptest1.dbInterface.ItemLocalDataSource
import org.koin.dsl.module

fun androidSharedModule(
    museumApi: MuseumApi,
    museumStorage: MuseumStorage,
    itemLocalDataSource: ItemLocalDataSource
) = module {
    single { museumApi }
    single { museumStorage }
    single { itemLocalDataSource }
}