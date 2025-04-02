package org.example.apptest1

import android.app.Application
import org.example.apptest1.di.initKoin
import org.example.apptest1.screens.DetailViewModel
import org.example.apptest1.screens.ListViewModel
import org.koin.dsl.module

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                module {
                    factory { ListViewModel(get()) }
                    factory { DetailViewModel(get()) }
                }
            )
        )
    }
}