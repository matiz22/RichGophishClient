package gophish.presentation.screens

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
import config.presentation.events.ScaffoldEvents
import config.presentation.navigation.ConfigScreensConfiguration
import config.presentation.states.FloatingActionButtonState
import gophish.presentation.components.EmailTemplatesComponent
import gophish.presentation.navigation.EmailTemplateConfiguration
import root.presentation.openHTML

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun EmailTemplatesScreens(
    emailComponent: EmailTemplatesComponent,
    configComponent: ConfigComponent
) {
    val childStack by emailComponent.childStack.subscribeAsState()
    val snackBarState = configComponent.snackbarHostState
    val configNavigation = configComponent.navigation
    val emailNavigation = emailComponent.navigation

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is EmailTemplatesComponent.Child.CreateEmailScreen -> {
                val createForm by instance.component.createTemplateForm
                val apiCallResult = instance.component.apiCallResult
                LaunchedEffect(apiCallResult) {
                    apiCallResult.collect { apiCallResult ->
                        if (apiCallResult.successful) {
                            snackBarState.showSnackbar(
                                message = AppRes.string.successful_operation
                            )
                            emailNavigation.pop()
                        } else {
                            if (apiCallResult.errorMessage != null) {
                                snackBarState.showSnackbar(
                                    message = apiCallResult.errorMessage!!
                                )
                            }else{
                                snackBarState.showSnackbar(
                                    message = AppRes.string.api_call_failed
                                )
                            }
                        }
                    }
                }
                CreateEmailTemplateScreen(
                    form = createForm,
                    onEvent = instance.component::onEvent,
                    navigateBack = {
                        emailComponent.navigation.pop()
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

            is EmailTemplatesComponent.Child.EmailListScreen -> {
                val templates by instance.component.templates.collectAsState()

                LaunchedEffect(Unit) {
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            FloatingActionButtonState(
                                action = {
                                    emailComponent.navigation.pushNew(EmailTemplateConfiguration.CreateEmailTemplateConfiguration)
                                },
                                icon = Icons.Default.Add
                            )
                        )
                    )
                }
                EmailListTemplatesScreen(
                    templates = templates,
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