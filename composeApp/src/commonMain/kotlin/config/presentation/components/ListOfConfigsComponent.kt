package config.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import auth.domain.model.User
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import config.presentation.events.ListOfConfigsEvent
import config.presentation.states.ConfigFormState
import configs.di.ConfigKoinComponent
import configs.domain.model.ConfigsOrError
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ListOfConfigsComponent(
    componentContext: ComponentContext,
    private val user: User
) : ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val configRepository = ConfigKoinComponent().configRepository

    private val _configs = MutableStateFlow<ConfigsOrError>(ConfigsOrError())
    val configs: StateFlow<ConfigsOrError> = _configs

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    var configFormState by mutableStateOf(ConfigFormState(name = "test"))


    fun onEvent(listOfConfigsEvent: ListOfConfigsEvent) {
        when (listOfConfigsEvent) {
            is ListOfConfigsEvent.AddConfig -> {
                addConfig()
                updateConfigs()
            }
        }
    }

    init {
        lifecycle.doOnDestroy { scope.cancel() }
        updateConfigs()
    }

    private fun updateConfigs() {
        scope.launch {
            val configsOrError = configRepository.getConfigsForUser(user.id)
            _configs.emit(configsOrError)
        }
    }

    private fun addConfig() {
        scope.launch {
            val result = configRepository.createConfig(
                createGophishConfig = configFormState.toCreateGophishConfig(
                    user.id
                )
            )
            _apiCallResult.send(result)
        }
    }

}
