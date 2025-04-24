import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.apptest1.db.AppDatabase
import org.example.apptest1.dbInterface.ItemLocalDataSource
import org.example.apptest1.di.doInitKoin
import org.koin.dsl.module

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


// Wrapper to expose to Swift
fun initKoinForIos() {
    doInitKoin(iosModule)
}