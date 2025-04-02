package org.example.apptest1

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            Navigator(ListScreen()) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}

class ListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = cafe.adriel.voyager.navigator.LocalNavigator.currentOrThrow
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Item 1", modifier = Modifier.clickable {
                navigator.push(DetailScreen("1"))
            })
            Spacer(modifier = Modifier.height(8.dp))
            Text("Item 2", modifier = Modifier.clickable {
                navigator.push(DetailScreen("2"))
            })
        }
    }
}

class DetailScreen(private val objectId: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = cafe.adriel.voyager.navigator.LocalNavigator.currentOrThrow
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Detail Screen for Object ID: $objectId")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navigator.pop() }) {
                Text("Go Back")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KMP Desktop App") {
        App()
    }
}