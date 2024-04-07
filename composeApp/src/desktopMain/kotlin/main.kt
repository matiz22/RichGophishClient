import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import helper.ApplicationState
import helper.HtmlViewerWindow
import navigation.RootComponent
import root.di.initKoin

import utils.runOnUiThread

val applicationState by mutableStateOf(ApplicationState())

fun main() {
    initKoin()
    application {
        val lifecycle = LifecycleRegistry()
        val root = runOnUiThread {
            RootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
            )
        }
        Window(
            onCloseRequest = ::exitApplication
        ) {
            App(root = root)
        }
        for (window in applicationState.htmlWindows) {
            key(window) {
                this.HtmlViewerWindow(window)
            }
        }

    }
}

