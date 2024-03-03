package config.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.matiz22.richgophishclient.AppRes
import config.presentation.components.ConfigComponent
import root.presentation.composables.AppScaffold


@Composable
fun ConfigScreen(component: ConfigComponent) {
    val childStack by component.childStack.subscribeAsState()
    AppScaffold(
        title = AppRes.string.home_page,
        content = {
            Children(
                stack = childStack,
                animation = stackAnimation(slide())
            ) { child ->
                when (val instance = child.instance) {
                    is ConfigComponent.Child.ListOfConfigsScreenChild -> {
                        ListOfConfigsScreen()
                    }

                    is ConfigComponent.Child.HomeOfConfigsScreenChild -> {

                    }
                }
            }
        }
    )
}

