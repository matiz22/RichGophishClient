package config.presentation.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.pop
import com.matiz22.richgophishclient.AppRes
import config.presentation.components.ConfigComponent
import root.presentation.composables.AppScaffold


@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun ConfigScreen(component: ConfigComponent) {
    val childStack by component.childStack.subscribeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    AppScaffold(
        title = AppRes.string.home_page,
        snackbarHostState = snackbarHostState,
        content = {
            Children(
                stack = childStack, animation = stackAnimation(slide())
            ) { child ->
                when (val instance = child.instance) {
                    is ConfigComponent.Child.ListOfConfigsScreenChild -> {
                        val apiCallResult = instance.component.apiCallResult
                        LaunchedEffect(apiCallResult) {
                            apiCallResult.collect { result ->
                                if (result.successful) {
                                    snackbarHostState.showSnackbar(
                                        AppRes.string.successful_operation
                                    )
                                } else {
                                    if (result.errorMessage != null) snackbarHostState.showSnackbar(
                                        message = result.errorMessage!!
                                    )
                                }
                            }
                        }
                        val configsOrError by instance.component.configs.collectAsState()
                        ListOfConfigsScreen(configsOrError, onEvent = instance.component::onEvent)
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



