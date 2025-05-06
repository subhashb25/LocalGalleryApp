package org.example.apptest1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.apptest1.di.initKoin

fun main() = application {
    initKoin(listOf(jvmModule))
    Window(onCloseRequest = ::exitApplication, title = "KMP Desktop App") {
        App()
    }
}

