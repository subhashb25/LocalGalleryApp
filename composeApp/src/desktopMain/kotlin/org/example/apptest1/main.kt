package org.example.apptest1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "LocalGalleryApp",
    ) {
        App()
    }
}