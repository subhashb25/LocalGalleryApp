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
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


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

fun initKoin(extraModules: List<Module> = emptyList()) {
    startKoin {
        modules(
            dataModule, //common
            *extraModules.toTypedArray(), // Platform-specific modules (Android, iOS)
        )
    }
}
