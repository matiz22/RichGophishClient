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
import config.presentation.states.FloatingActionButtonState
import gophish.presentation.components.UserGroupComponent
import gophish.presentation.events.UserGroupListEvent
import gophish.presentation.navigation.UserGroupConfiguration

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun UserGroupScreen(
    userGroupComponent: UserGroupComponent,
    configComponent: ConfigComponent
) {
    val childStack by userGroupComponent.childStack.subscribeAsState()
    val snackBarState = configComponent.snackbarHostState
    val userGroupNavigation = userGroupComponent.navigation

    Children(
        stack = childStack, animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is UserGroupComponent.Child.CreateUserGroupChild -> {
                val createForm by instance.component.createUserGroupForm
                val createTargetForm by instance.component.createTargetForm
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
                CreateUserGroupScreen(
                    form = createForm,
                    onEvent = instance.component::onEvent,
                    createTargetForm = createTargetForm,
                    navigateBack = {
                        userGroupNavigation.pop()
                    },
                )
            }

            is UserGroupComponent.Child.UserGroupListChild -> {
                val userGroups by instance.component.userGroups.collectAsState()
                val pickedGroup = instance.component.pickedGroup

                LaunchedEffect(Unit) {
                    configComponent.onEvent(
                        ScaffoldEvents.UpdateFloatingActionButton(
                            FloatingActionButtonState(
                                action = {
                                    userGroupNavigation.pushNew(UserGroupConfiguration.CreateUserGroupConfiguration)
                                },
                                icon = Icons.Default.Add
                            )
                        )
                    )
                }
                UserGroupsListScreen(
                    userGroups = userGroups,
                    pickedGroup = pickedGroup,
                    unPickGroup = {
                        instance.component.onEvent(UserGroupListEvent.UnPickUserGroup)
                    },
                    navigateToDetails = { group ->
                        instance.component.onEvent(UserGroupListEvent.PickUserGroup(group))
                    }
                )
            }
        }
    }
}