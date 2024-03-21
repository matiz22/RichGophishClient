package config.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import auth.domain.model.User
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.matiz22.richgophishclient.AppRes
import config.presentation.events.ConfigDialogMode
import config.presentation.events.ListOfConfigsEvent
import config.presentation.states.ConfigFormState
import configs.di.ConfigKoinComponent
import configs.di.ValidateConfigKoinComponent
import configs.domain.model.ConfigsOrError
import home.di.ValidatorsComponent
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ListOfConfigsComponent(
    componentContext: ComponentContext,
    private val user: User
) : ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val configRepository = ConfigKoinComponent().configRepository

    private val urlValidator = ValidatorsComponent().urlValidator
    private val nameValidator = ValidateConfigKoinComponent().validateName
    private val apiKeyValidator = ValidateConfigKoinComponent().validateApiKey

    private val _configs = MutableStateFlow<ConfigsOrError>(ConfigsOrError())
    val configs: StateFlow<ConfigsOrError> = _configs.asStateFlow()

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    var configFormState by mutableStateOf(ConfigFormState())


    fun onEvent(listOfConfigsEvent: ListOfConfigsEvent) {
        when (listOfConfigsEvent) {
            is ListOfConfigsEvent.AddConfig -> {
                val isCorrect = checkConfigForm()
                if (isCorrect) {
                    scope.launch {
                        val addResult = addConfig().await()
                        if (addResult.successful) {
                            updateConfigs()
                            configFormState = ConfigFormState()
                        }
                    }
                }
            }

            is ListOfConfigsEvent.DeleteConfig -> {
                scope.launch {
                    val result = deleteConfig(listOfConfigsEvent.id).await()
                    if (result.successful) {
                        updateConfigs()
                    }
                }

            }

            is ListOfConfigsEvent.EditConfig -> {
                val isCorrect = checkConfigForm()
                if (isCorrect) {
                    scope.launch {
                        val editResult = editConfig().await()
                        if (editResult.successful) {
                            updateConfigs()
                            configFormState = ConfigFormState()
                        }
                    }
                }
            }

            is ListOfConfigsEvent.HandleDialog -> {
                if (configFormState.isShown) {
                    configFormState = ConfigFormState()
                } else {
                    configFormState = if (listOfConfigsEvent.gophishConfig != null) {
                        configFormState.copy(
                            isShown = true,
                            id = listOfConfigsEvent.gophishConfig.id,
                            name = listOfConfigsEvent.gophishConfig.name,
                            url = listOfConfigsEvent.gophishConfig.url,
                            apiKey = listOfConfigsEvent.gophishConfig.apiKey,
                            mode = ConfigDialogMode.EDIT
                        )
                    } else {
                        configFormState.copy(isShown = true)
                    }
                }
            }

            is ListOfConfigsEvent.OnApiKeyUpdate -> {
                configFormState = configFormState.copy(apiKey = listOfConfigsEvent.apiKey)
            }

            is ListOfConfigsEvent.OnNameUpdate -> {
                configFormState = configFormState.copy(name = listOfConfigsEvent.name)
            }

            is ListOfConfigsEvent.OnUrlUpdate -> {
                configFormState = configFormState.copy(url = listOfConfigsEvent.url)
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

    private fun addConfig(): Deferred<ApiCallResult> {
        return scope.async {
            val result = configRepository.createConfig(
                createGophishConfig = configFormState.toCreateGophishConfig(
                    user.id
                )
            )
            withContext(Dispatchers.Main) {
                _apiCallResult.send(result)
            }
            result
        }
    }

    private fun editConfig(): Deferred<ApiCallResult> {
        return scope.async {
            if (configFormState.id != null) {
                val result = configRepository.editConfig(
                    configFormState.toEditGophishConfig(
                        id = configFormState.id!!,
                        userId = user.id
                    )
                )
                withContext(Dispatchers.Main) {
                    runBlocking {
                        _apiCallResult.send(result)
                    }
                }
                result
            } else {
                ApiCallResult(
                    successful = false,
                    errorMessage = AppRes.string.api_call_failed
                )
            }
        }
    }

    private fun deleteConfig(id: Long): Deferred<ApiCallResult> {
        return scope.async {
            val result = configRepository.deleteConfig(id)
            withContext(Dispatchers.Main) {
                runBlocking {
                    _apiCallResult.send(result)
                }
            }
            result
        }
    }

    private fun checkConfigForm(): Boolean {
        val nameValidationResult = nameValidator.execute(configFormState.name)
        val urlValidationResult = urlValidator.execute(configFormState.url)
        val apiKeyValidationResult = apiKeyValidator.execute(configFormState.apiKey)

        val isCorrect = listOf(
            nameValidationResult,
            urlValidationResult,
            apiKeyValidationResult
        ).all {
            it.successful
        }

        if (!isCorrect) {
            configFormState = configFormState.copy(
                nameError = nameValidationResult.errorMessage,
                urlError = urlValidationResult.errorMessage,
                apiKeyError = apiKeyValidationResult.errorMessage
            )
        }
        return isCorrect

    }

}
