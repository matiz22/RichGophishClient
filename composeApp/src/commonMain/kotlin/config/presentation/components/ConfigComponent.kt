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
import config.presentation.states.FloatingActionButtonState
import gophish.components.CampaignDetailsComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


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
    var floatingActionButtonState by mutableStateOf<FloatingActionButtonState?>(null)
    var snackbarHostState by mutableStateOf(SnackbarHostState())

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
        }
    }

    sealed class Child {
        data class ListOfConfigsScreenChild(val component: ListOfConfigsComponent) : Child()
        data class HomeOfConfigScreenChild(val component: HomeOfConfigComponent) : Child()
        data class CampaignDetailsChild(val component: CampaignDetailsComponent) : Child()
    }
}