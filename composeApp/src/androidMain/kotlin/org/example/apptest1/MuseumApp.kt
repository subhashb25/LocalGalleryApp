package org.example.apptest1

import android.app.Application
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import org.example.apptest1.di.androidSharedModule
import org.example.apptest1.di.initKoin
import org.example.apptest1.screens.details.DetailViewModel
import org.example.apptest1.screens.list.ListViewModel
import org.koin.dsl.module

@HiltAndroidApp
class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // 🔹 Access Hilt-provided dependencies using an EntryPoint.
        // This allows Koin to reuse objects already created by Hilt.
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            this,
            AppDependenciesEntryPoint::class.java   // This interface exposes Hilt-provided dependencies
        )

        // 🔹 Define a Koin module that "bridges" Hilt dependencies into Koin.
        // We're taking Hilt-injected instances and telling Koin to use those.
        val bridgeModule = androidSharedModule(
            hiltEntryPoint.itemLocalDataSource()
        )

        // 🔹 Initialize Koin with:
        // 1. The bridge module that imports Hilt dependencies.
        // 2. ViewModels that rely on shared code dependencies (like MuseumRepository).

        initKoin(
            listOf(
                bridgeModule,
                module {
                    factory { ListViewModel(get()) }        // Requires MuseumRepository
                    factory { DetailViewModel(get()) }      // Requires MuseumRepository
                }
            )
        )
    }
}