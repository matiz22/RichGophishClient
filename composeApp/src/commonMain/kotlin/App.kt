import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import auth.presentation.screen.AuthScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import config.presentation.screen.ConfigScreen
import navigation.RootComponent
import ui.SchemeResolver


@Composable
fun App(root: RootComponent) {

    MaterialTheme(
        colorScheme = SchemeResolver.provideColorScheme()
    ) {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instacnce = child.instance) {
                is RootComponent.Child.AuthScreen -> AuthScreen(instacnce.component)
                is RootComponent.Child.ConfigScreen -> ConfigScreen(instacnce.component)
            }
        }
    }
}