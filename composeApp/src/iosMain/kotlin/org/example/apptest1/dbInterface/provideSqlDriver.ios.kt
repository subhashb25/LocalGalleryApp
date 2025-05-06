package org.example.apptest1.dbInterface

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.apptest1.db.AppDatabase

actual fun provideSqlDriver(): SqlDriver {
    val driver = NativeSqliteDriver(AppDatabase.Schema, "app.db")
    return driver
}