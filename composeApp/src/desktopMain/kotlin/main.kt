import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import home.di.initKoin
import navigation.RootComponent

fun main() = application {
    initKoin()
    val root = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()))
    }
    Window(
        onCloseRequest = ::exitApplication
    ) {
        App(root = root)
    }
}

