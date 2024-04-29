package gophish.page.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import config.presentation.events.ScaffoldEvents
import config.presentation.navigation.ConfigScreensConfiguration
import config.presentation.states.IconButtonState
import gophish.page.components.PagesComponent
import gophish.page.navigation.PagesConfiguration
import root.presentation.openHTML

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun PagesScreens(
    pagesComponent: PagesComponent,
    configComponent: ConfigComponent
) {
    val childStack by pagesComponent.childStack.subscribeAsState()
    val snackBarState = configComponent.snackbarHostState
    val pagesNavigation = pagesComponent.navigation
    val configNavigation = configComponent.navigation

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is PagesComponent.Child.CreatePageChild -> {
                val createForm by instance.component.createPageForm
                val apiCallResult = instance.component.apiCallResult
                LaunchedEffect(Unit) {
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            null
                        )
                    )
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateLeadingIconButton(
                            IconButtonState(
                                action = {
                                    pagesNavigation.pop()
                                },
                                icon = Icons.Default.ArrowBack
                            )
                        )
                    )
                }
                LaunchedEffect(apiCallResult) {
                    apiCallResult.collect { apiCallResult ->
                        if (apiCallResult.successful) {
                            snackBarState.showSnackbar(
                                message = AppRes.string.successful_operation
                            )
                            pagesNavigation.pop()
                        } else {
                            if (apiCallResult.errorMessage != null) {
                                snackBarState.showSnackbar(
                                    message = apiCallResult.errorMessage!!
                                )
                            } else {
                                snackBarState.showSnackbar(
                                    message = AppRes.string.api_call_failed
                                )
                            }
                        }
                    }
                }
                CreatePageScreen(
                    form = createForm,
                    onEvent = instance.component::onEvent,
                    navigateBack = {
                        pagesNavigation.pop()
                    },
                    onPreview = { template ->
                        openHTML(
                            title = template.name,
                            data = template.html,
                            navigate = {
                                configComponent.navigation.pushNew(
                                    ConfigScreensConfiguration.HtmlViewerConfiguration(
                                        title = template.name,
                                        data = template.html
                                    )
                                )
                            }
                        )
                    }
                )
            }

            is PagesComponent.Child.PagesListChild -> {
                val pages by instance.component.pages.collectAsState()

                LaunchedEffect(Unit) {
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            IconButtonState(
                                action = {
                                    pagesNavigation.pushNew(PagesConfiguration.CreatePageConfiguration)
                                },
                                icon = Icons.Default.Add
                            )
                        )
                    )
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateLeadingIconButton(
                            IconButtonState(
                                action = {
                                    configNavigation.pop()
                                },
                                icon = Icons.Default.ArrowBack
                            )
                        )
                    )
                }
                PagesListScreen(
                    pages = pages,
                    navigateToDetails = { template ->
                        openHTML(
                            title = template.name,
                            data = template.html,
                            navigate = {
                                configComponent.navigation.pushNew(
                                    ConfigScreensConfiguration.HtmlViewerConfiguration(
                                        title = template.name,
                                        data = template.html
                                    )
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}