package org.example.apptest1.dbInterface

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.apptest1.db.AppDatabase

actual fun provideSqlDriver(): SqlDriver {
    val driver = JdbcSqliteDriver("jdbc:sqlite:app.db")
    AppDatabase.Schema.create(driver)
    return driver
}