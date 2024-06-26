package config.presentation.screen

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
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import config.presentation.components.ConfigComponent
import config.presentation.events.ListOfConfigsEvent
import config.presentation.events.ScaffoldEvents
import config.presentation.navigation.ConfigScreensConfiguration
import config.presentation.states.IconButtonState
import gophish.campaign.screens.CampaignDetailsScreen
import gophish.campaign.screens.CreateCampaignScreen
import gophish.email_templates.screens.EmailTemplatesScreens
import gophish.page.screens.PagesScreens
import gophish.smtp.screens.SmtpScreen
import gophish.user_groups.screens.UserGroupScreen
import root.presentation.composables.AppScaffold
import root.presentation.screens.HtmlViewerScreen


@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun ConfigScreen(configComponent: ConfigComponent) {
    val childStack by configComponent.childStack.subscribeAsState()
    val snackbarHostState = configComponent.snackbarHostState
    val floatingActionButtonState = configComponent.floatingActionButtonState
    val leadingIconButtonState = configComponent.leadingIconButtonState

    AppScaffold(
        title = AppRes.string.app_name,
        snackbarHostState = snackbarHostState,
        floatingActionButtonState = floatingActionButtonState,
        leadingIconButtonState = leadingIconButtonState,
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
                                    IconButtonState(
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
                                    ),
                                    onComplete = {
                                        configComponent.onEvent(
                                            ScaffoldEvents.UpdateFloatingActionButton(null)
                                        )
                                    })
                            }
                        )
                    }

                    is ConfigComponent.Child.HomeOfConfigScreenChild -> {
                        val summary = instance.component.stats
                        val campaignsOrError by instance.component.campaigns.collectAsState()
                        val apiCallResultChannel = instance.component.apiCallResult
                        val pickingCampaign = instance.component.pickingCampaign
                        val pickedCampaign = instance.component.pickedCampaignChannel
                        LaunchedEffect(apiCallResultChannel) {
                            apiCallResultChannel.collect { result ->
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
                        LaunchedEffect(Unit) {
                            instance.component.updateCampaigns()
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateFloatingActionButton(
                                    IconButtonState(
                                        action = {
                                            configComponent.navigation.pushNew(
                                                ConfigScreensConfiguration.CreateCampaignConfiguration
                                            )
                                        },
                                        icon = Icons.Default.Add
                                    )
                                )
                            )
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateLeadingIconButton(
                                    IconButtonState(
                                        action = { configComponent.navigation.pop() },
                                        icon = Icons.Default.ArrowBack
                                    )
                                )
                            )
                        }
                        LaunchedEffect(pickedCampaign) {
                            pickedCampaign.collect { campaign ->
                                configComponent.navigation.pushNew(
                                    ConfigScreensConfiguration.CampaignDetailsConfiguration(
                                        campaign
                                    )
                                )
                            }
                        }
                        HomeOfConfigScreen(
                            pickingCampaign = pickingCampaign,
                            summary = summary,
                            campaigns = campaignsOrError,
                            onEvent = instance.component::onEvent,
                            navigate = { configuration ->
                                configComponent.navigation.pushNew(configuration)
                            }
                        )
                    }

                    is ConfigComponent.Child.CampaignDetailsChild -> {
                        val campaign by instance.component.campaign.collectAsState()
                        val campaignSummary by instance.component.campaignSummary.collectAsState()
                        val pageState = instance.component.pageState
                        val searchText by instance.component.searchText.collectAsState()
                        val pickedUserForDetails = instance.component.pickedUserForDetails

                        LaunchedEffect(Unit) {
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateFloatingActionButton(
                                    null
                                )
                            )
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateLeadingIconButton(
                                    IconButtonState(
                                        action = { configComponent.navigation.pop() },
                                        icon = Icons.Default.ArrowBack
                                    )
                                )
                            )
                        }

                        CampaignDetailsScreen(
                            campaign = campaign,
                            campaignSummary = campaignSummary,
                            pageState = pageState,
                            onEvent = instance.component::onEvent,
                            searchText = searchText,
                            pickedUserForDetails = pickedUserForDetails
                        )
                    }

                    is ConfigComponent.Child.EmailTemplatesChild -> {
                        EmailTemplatesScreens(
                            emailComponent = instance.component,
                            configComponent = configComponent
                        )
                    }

                    is ConfigComponent.Child.HtmlViewerChild -> {
                        val webViewState =
                            rememberWebViewStateWithHTMLData(data = instance.component.data)
                        LaunchedEffect(Unit) {
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateFloatingActionButton(
                                    null
                                )
                            )
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateLeadingIconButton(
                                    IconButtonState(
                                        action = { configComponent.navigation.pop() },
                                        icon = Icons.Default.ArrowBack
                                    )
                                )
                            )
                        }
                        HtmlViewerScreen(instance.component.title, webViewState)
                    }

                    is ConfigComponent.Child.PagesChild -> {
                        PagesScreens(
                            pagesComponent = instance.component,
                            configComponent = configComponent
                        )
                    }

                    is ConfigComponent.Child.UserGroupChild -> {
                        UserGroupScreen(
                            userGroupComponent = instance.component,
                            configComponent = configComponent
                        )
                    }

                    is ConfigComponent.Child.SmtpChild -> {
                        SmtpScreen(
                            smtpComponent = instance.component,
                            configComponent = configComponent
                        )
                    }

                    is ConfigComponent.Child.CreateCampaignChild -> {
                        val form = instance.component.campaignFormState
                        val pages by instance.component.pagesNames.collectAsState()
                        val smtps by instance.component.smtpsNames.collectAsState()
                        val groups by instance.component.groupsNames.collectAsState()
                        val templates by instance.component.templatesNames.collectAsState()
                        val apiCallResult = instance.component.apiCallResult

                        LaunchedEffect(apiCallResult) {
                            apiCallResult.collect { result ->
                                if (result.successful) {
                                    configComponent.onEvent(
                                        ScaffoldEvents.ShowSnackBar(
                                            message = AppRes.string.successful_operation
                                        )
                                    )
                                    configComponent.navigation.pop()

                                } else {
                                    if (result.errorMessage != null) configComponent.onEvent(
                                        ScaffoldEvents.ShowSnackBar(message = result.errorMessage!!)
                                    )
                                }
                            }
                        }

                        LaunchedEffect(Unit) {
                            instance.component.getAvailableResources()
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateFloatingActionButton(
                                    null
                                )
                            )
                            configComponent.onEvent(
                                ScaffoldEvents.UpdateLeadingIconButton(
                                    IconButtonState(
                                        action = { configComponent.navigation.pop() },
                                        icon = Icons.Default.ArrowBack
                                    )
                                )
                            )
                        }

                        CreateCampaignScreen(
                            form = form,
                            pages = pages,
                            groups = groups,
                            templates = templates,
                            smtps = smtps ,
                            onEvent = instance.component::onEvent
                        )
                    }
                }
            }
        }
    )
}
