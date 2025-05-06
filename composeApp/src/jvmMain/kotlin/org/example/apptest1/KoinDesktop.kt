package org.example.apptest1

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.apptest1.db.AppDatabase
import org.example.apptest1.dbInterface.ItemLocalDataSource
import org.example.apptest1.dbInterface.provideSqlDriver
import org.koin.dsl.module


val jvmModule = module {
    single<SqlDriver> { provideSqlDriver() }
    single {
        AppDatabase(get())
    }

    single {
        ItemLocalDataSource(get())
    }
}

