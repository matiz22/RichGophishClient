package gophish.smtp.screens

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
import config.presentation.states.IconButtonState
import gophish.smtp.components.SmtpComponent
import gophish.smtp.navigation.SmtpConfiguration

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun SmtpScreen(
    smtpComponent: SmtpComponent,
    configComponent: ConfigComponent
) {
    val childStack by smtpComponent.childStack.subscribeAsState()
    val snackBarState = configComponent.snackbarHostState
    val smtpNavigation = smtpComponent.navigation
    val configNavigation = configComponent.navigation

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is SmtpComponent.Child.CreateSmtpChild-> {
                val createForm by instance.component.createSmtpForm
                val headerForm by instance.component.createHeaderForm
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
                                    smtpNavigation.pop()
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
                            smtpNavigation.pop()
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
                CreateSmtpScreen(
                    form = createForm,
                    onEvent = instance.component::onEvent,
                    headerForm = headerForm,
                )
            }

            is SmtpComponent.Child.SmtpListChild -> {
                val smtpList by instance.component.smtpList.collectAsState()
                val apiCallResult = instance.component.apiCallResult
                LaunchedEffect(apiCallResult) {
                    apiCallResult.collect { apiCallResult ->
                        if (apiCallResult.successful) {
                            snackBarState.showSnackbar(
                                message = AppRes.string.successful_operation
                            )
                            smtpNavigation.pop()
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

                LaunchedEffect(Unit) {
                    instance.component.updateSmtp()
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            IconButtonState(
                                action = {
                                    smtpNavigation.pushNew(SmtpConfiguration.CreateSmtpConfiguration)
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
                SmtpListScreen(
                    smtpList = smtpList,
                    onEvent = instance.component::onEvent
                )
            }
        }
    }
}