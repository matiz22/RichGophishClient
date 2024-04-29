package gophish.user_groups.screens

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
import gophish.user_groups.components.UserGroupComponent
import gophish.user_groups.events.UserGroupListEvent
import gophish.user_groups.navigation.UserGroupConfiguration

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun UserGroupScreen(
    userGroupComponent: UserGroupComponent,
    configComponent: ConfigComponent
) {
    val childStack by userGroupComponent.childStack.subscribeAsState()
    val snackBarState = configComponent.snackbarHostState
    val userGroupNavigation = userGroupComponent.navigation
    val configNavigation = configComponent.navigation

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is UserGroupComponent.Child.CreateUserGroupChild -> {
                val createForm by instance.component.createUserGroupForm
                val createTargetForm by instance.component.createTargetForm
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
                                    userGroupNavigation.pop()
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
                CreateUserGroupScreen(
                    form = createForm,
                    onEvent = instance.component::onEvent,
                    createTargetForm = createTargetForm,
                )
            }

            is UserGroupComponent.Child.UserGroupListChild -> {
                val userGroups by instance.component.userGroups.collectAsState()
                val targetList = instance.component.pickedGroup
                val apiCallResult = instance.component.apiCallResult

                LaunchedEffect(Unit) {
                    instance.component.updateUserGroups()
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            IconButtonState(
                                action = {
                                    userGroupNavigation.pushNew(UserGroupConfiguration.CreateUserGroupConfiguration)
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
                UserGroupsListScreen(
                    userGroups = userGroups,
                    pickedTargets = targetList,
                    onEvent = instance.component::onEvent,
                )
            }
        }
    }
}