package org.example.apptest1

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application

@Composable
fun App() {
    Text("Hello, Desktop KMP!")
}

fun main() = application {
    MaterialTheme {
        App()
    }

}