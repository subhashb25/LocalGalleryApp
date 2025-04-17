package org.example.apptest1.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.apptest1.db.AppDatabase
import org.example.apptest1.dbInterface.ItemLocalDataSource
import org.koin.dsl.module

fun doInitKoin() {
    initKoin(extraModules = listOf(iosModule))
}

val iosModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(AppDatabase.Schema, "app.db")
    }

    single {
        AppDatabase(get())
    }

    single {
        ItemLocalDataSource(get())
    }
}