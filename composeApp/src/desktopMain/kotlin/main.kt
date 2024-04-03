import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import helper.ApplicationState
import root.di.initKoin
import navigation.RootComponent
import utils.runOnUiThread

val applicationState by mutableStateOf(ApplicationState())


fun main() = application {
    initKoin()
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
}

