import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import root.di.initKoin
import navigation.RootComponent
import utils.runOnUiThread

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

