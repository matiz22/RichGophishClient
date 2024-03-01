package config.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import config.presentation.components.ConfigScreenComponent


@Composable
fun ConfigScreen(component: ConfigScreenComponent) {
    val childStack by component.childStack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is ConfigScreenComponent.Child.ListOfConfigsScreenChild -> {
                ListOfConfigs()
            }
        }
    }
}

