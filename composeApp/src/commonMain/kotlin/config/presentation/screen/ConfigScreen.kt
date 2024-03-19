package config.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import config.presentation.events.ListOfConfigsEvent
import config.presentation.events.ScaffoldEvents
import config.presentation.navigation.ConfigScreensConfiguration
import config.presentation.states.FloatingActionButtonState
import root.presentation.composables.AppScaffold


@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun ConfigScreen(configComponent: ConfigComponent) {
    val childStack by configComponent.childStack.subscribeAsState()
    val snackbarHostState = configComponent.snackbarHostState
    val floatingActionButtonState = configComponent.floatingActionButtonState

    AppScaffold(
        title = AppRes.string.home_page,
        snackbarHostState = snackbarHostState,
        floatingActionButtonState = floatingActionButtonState,
        content = {
            Children(
                stack = childStack, animation = stackAnimation(slide())
            ) { child ->
                when (val instance = child.instance) {
                    is ConfigComponent.Child.ListOfConfigsScreenChild -> {
                        val apiCallResult = instance.component.apiCallResult
                        val configFormState = instance.component.configFormState
                        LaunchedEffect(Unit) {
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateFloatingActionButton(
                                    FloatingActionButtonState(
                                        action = {
                                            instance.component.onEvent(ListOfConfigsEvent.HandleDialog())
                                        },
                                        icon = Icons.Default.Add
                                    )
                                )
                            )
                        }
                        LaunchedEffect(apiCallResult) {
                            apiCallResult.collect { result ->
                                if (result.successful) {
                                    configComponent.onEvent(
                                        ScaffoldEvents.ShowSnackBar(
                                            message = AppRes.string.successful_operation
                                        )
                                    )
                                } else {
                                    if (result.errorMessage != null) configComponent.onEvent(
                                        ScaffoldEvents.ShowSnackBar(message = result.errorMessage!!)
                                    )
                                }
                            }
                        }
                        val configsOrError by instance.component.configs.collectAsState()
                        ListOfConfigsScreen(
                            configsOrError = configsOrError,
                            configFormState = configFormState,
                            onEvent = instance.component::onEvent,
                            navigate = { config ->
                                configComponent.navigation.pushNew(
                                    ConfigScreensConfiguration.HomeOfConfigConfiguration(
                                        config
                                    ), onComplete = { configComponent.onEvent(ScaffoldEvents.UpdateFloatingActionButton(null))})
                            }
                        )
                    }

                    is ConfigComponent.Child.HomeOfConfigScreenChild -> {
                        HomeOfConfigScreen()
                    }
                }
            }
        }
    )
}



