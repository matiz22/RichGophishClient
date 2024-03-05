package config.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.matiz22.richgophishclient.AppRes
import config.presentation.components.ConfigComponent
import config.presentation.navigation.ConfigScreensConfiguration
import configs.domain.model.GophishConfig
import root.presentation.composables.AppScaffold


@OptIn(ExperimentalDecomposeApi::class)
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
                        ListOfConfigsScreen(next = {
                            component.navigation.pushNew(
                                configuration = ConfigScreensConfiguration.HomeOfConfigConfiguration(
                                    gophishConfig = GophishConfig(
                                        0,
                                        "test",
                                        0,
                                        "",
                                        ""
                                    )
                                )
                            )
                        })
                    }

                    is ConfigComponent.Child.HomeOfConfigScreenChild -> {
                        HomeOfConfigScreen(previous = {
                            component.navigation.pop()
                        })
                    }
                }
            }
        }
    )
}



