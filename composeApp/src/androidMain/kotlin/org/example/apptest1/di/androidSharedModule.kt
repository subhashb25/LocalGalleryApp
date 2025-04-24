package org.example.apptest1.di

import org.example.apptest1.dbInterface.ItemLocalDataSource
import org.koin.dsl.module

fun androidSharedModule(
    itemLocalDataSource: ItemLocalDataSource
) = module {
    single { itemLocalDataSource }
}