package org.example.apptest1.di


import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.apptest1.data.InMemoryMuseumStorage
import org.example.apptest1.data.KtorMuseumApi
import org.example.apptest1.data.MuseumApi
import org.example.apptest1.data.MuseumRepository
import org.example.apptest1.data.MuseumStorage
import org.example.apptest1.screens.details.DetailViewModel
import org.example.apptest1.screens.list.ListViewModel
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin


val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(),get(), get()).apply {
            initialize()
        }
    }

}

val viewModelModule = module {
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)
}

fun initKoin(extraModules: List<Module> = emptyList()) {
    startKoin {
        modules(
            dataModule, //common
            viewModelModule,
            *extraModules.toTypedArray(), // Platform-specific modules (Android, iOS)
        )
    }
}

//iOS specific
var koinInstance: Koin? = null

fun doInitKoin(vararg modules: Module?) {
    koinInstance = startKoin {
        modules(dataModule) // common
        modules(*modules.filterNotNull().toTypedArray()) // Platform-specific modules (Android, iOS)
    }.koin // Store the Koin instance
}

fun provideListViewModel(): ListViewModel {
    return getKoin().get()
}

fun provideDetailsViewModel(): DetailViewModel {
    return getKoin().get()
}