import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import navigation.RootComponent

fun main() = application {
    val root = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()))
    }
    Window(onCloseRequest = ::exitApplication) {
        App(root = root)
    }
}

