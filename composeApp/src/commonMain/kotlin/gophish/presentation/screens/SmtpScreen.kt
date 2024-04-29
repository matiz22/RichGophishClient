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
import config.presentation.states.IconButtonState
import gophish.presentation.components.SmtpComponent
import gophish.presentation.events.SmtpListEvent
import gophish.presentation.events.UserGroupListEvent
import gophish.presentation.navigation.SmtpConfiguration

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun SmtpScreen(
    smtpComponent: SmtpComponent,
    configComponent: ConfigComponent
) {
    val childStack by smtpComponent.childStack.subscribeAsState()
    val snackBarState = configComponent.snackbarHostState
    val userGroupNavigation = smtpComponent.navigation

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is SmtpComponent.Child.CreateSmtpChild-> {
                val createForm by instance.component.createSmtpForm
                val headerForm by instance.component.createHeaderForm
                val apiCallResult = instance.component.apiCallResult
                LaunchedEffect(apiCallResult) {
                    apiCallResult.collect { apiCallResult ->
                        if (apiCallResult.successful) {
                            snackBarState.showSnackbar(
                                message = AppRes.string.successful_operation
                            )
                            userGroupNavigation.pop()
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
                val smtpList by instance.component.smptList.collectAsState()
                val pickedSmtp = instance.component.pickedSmtp

                LaunchedEffect(Unit) {
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            IconButtonState(
                                action = {
                                    userGroupNavigation.pushNew(SmtpConfiguration.CreateSmtpConfiguration)
                                },
                                icon = Icons.Default.Add
                            )
                        )
                    )
                }
                SmtpListScreen(
                    smtpList = smtpList,
                    pickedSmtp= pickedSmtp,
                    unPickSmtp = {
                        instance.component.onEvent(SmtpListEvent.UnPickSmtp)
                    },
                    navigateToDetails = { smtp ->
                        instance.component.onEvent(SmtpListEvent.PickSmtp(smtp))
                    }
                )
            }
        }
    }
}