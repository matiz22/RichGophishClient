package config.presentation.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import auth.domain.model.User
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.lifecycle.doOnDestroy
import config.presentation.events.ScaffoldEvents
import config.presentation.navigation.ConfigScreensConfiguration
import config.presentation.states.IconButtonState
import gophish.campaign.components.CampaignDetailsComponent
import gophish.campaign.components.CreateCampaignComponent
import gophish.email_templates.components.EmailTemplatesComponent
import gophish.page.components.PagesComponent
import gophish.smtp.components.SmtpComponent
import gophish.user_groups.components.UserGroupComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import root.presentation.components.HtmlViewerComponent


class ConfigComponent(
    componentContext: ComponentContext,
    val user: User
) : ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        lifecycle.doOnDestroy { coroutineScope.cancel() }
    }

    val navigation = StackNavigation<ConfigScreensConfiguration>()
    val childStack = childStack(
        source = navigation,
        serializer = ConfigScreensConfiguration.serializer(),
        initialConfiguration = ConfigScreensConfiguration.ListOfConfigsConfiguration(user),
        handleBackButton = true,
        childFactory = ::createChild
    )
    var floatingActionButtonState by mutableStateOf<IconButtonState?>(null)
    var snackbarHostState by mutableStateOf(SnackbarHostState())
    var leadingIconButtonState by mutableStateOf<IconButtonState?>(null)

    fun onEvent(scaffoldEvents: ScaffoldEvents) {
        when (scaffoldEvents) {
            is ScaffoldEvents.ShowSnackBar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(scaffoldEvents.message)
                }
            }

            is ScaffoldEvents.UpdateFloatingActionButton -> {
                floatingActionButtonState = scaffoldEvents.state
            }

            is ScaffoldEvents.UpdateLeadingIconButton -> {
                leadingIconButtonState = scaffoldEvents.state
            }
        }
    }

    private fun createChild(
        config: ConfigScreensConfiguration,
        context: ComponentContext
    ): Child {
        return when (config) {
            is ConfigScreensConfiguration.ListOfConfigsConfiguration -> Child.ListOfConfigsScreenChild(
                component = ListOfConfigsComponent(
                    componentContext = context,
                    user = config.user
                )
            )

            is ConfigScreensConfiguration.HomeOfConfigConfiguration -> Child.HomeOfConfigScreenChild(
                component = HomeOfConfigComponent(
                    componentContext = context,
                    config = config.gophishConfig
                )
            )

            is ConfigScreensConfiguration.CampaignDetailsConfiguration -> Child.CampaignDetailsChild(
                component = CampaignDetailsComponent(
                    componentContext = context,
                    selectedCampaign = config.campaign
                )
            )

            is ConfigScreensConfiguration.EmailTemplateConfiguration -> Child.EmailTemplatesChild(
                component = EmailTemplatesComponent(
                    componentContext = context
                )
            )

            is ConfigScreensConfiguration.HtmlViewerConfiguration -> Child.HtmlViewerChild(
                component = HtmlViewerComponent(
                    componentContext = context,
                    title = config.title,
                    data = config.data
                )
            )

            is ConfigScreensConfiguration.PagesConfiguration -> Child.PagesChild(
                component = PagesComponent(
                    context
                )
            )

            is ConfigScreensConfiguration.UserGroupConfiguration -> Child.UserGroupChild(
                component = UserGroupComponent(
                    componentContext = context
                )
            )

            is ConfigScreensConfiguration.SmtpConfiguration -> Child.SmtpChild(
                component = SmtpComponent(
                    componentContext = context
                )
            )

            is ConfigScreensConfiguration.CreateCampaignConfiguration -> Child.CreateCampaignChild(
                component = CreateCampaignComponent(
                    componentContext = context
                )
            )
        }
    }

    sealed class Child {
        data class ListOfConfigsScreenChild(val component: ListOfConfigsComponent) : Child()
        data class CreateCampaignChild(val component: CreateCampaignComponent) : Child()
        data class HomeOfConfigScreenChild(val component: HomeOfConfigComponent) : Child()
        data class CampaignDetailsChild(val component: CampaignDetailsComponent) : Child()
        data class EmailTemplatesChild(val component: EmailTemplatesComponent) : Child()
        data class HtmlViewerChild(val component: HtmlViewerComponent) : Child()
        data class PagesChild(val component: PagesComponent) : Child()
        data class UserGroupChild(val component: UserGroupComponent) : Child()
        data class SmtpChild(val component: SmtpComponent) : Child()
    }
}