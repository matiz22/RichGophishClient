package gophish.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.presentation.domain.CreateTemplateForm
import gophish.presentation.events.CreateEmailTemplatesEvent
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ollama.domain.repository.OllamaRepository
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import template.domain.repository.TemplateRepository


class CreateEmailTemplateComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy {
        getKoin().getOrCreateScope(
            "ollamaComponents",
            named("ollamaScope")
        )
    }
    private val ollamaRepository by inject<OllamaRepository>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _createTemplateForm = mutableStateOf(CreateTemplateForm())
    val createTemplateForm: State<CreateTemplateForm> = _createTemplateForm

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    private val templateScope: Scope by lazy { getKoin().getScope("gophishComponents") }
    val templateRepository = templateScope.get<TemplateRepository>()

    fun onEvent(emailTemplatesEvent: CreateEmailTemplatesEvent) {
        when (emailTemplatesEvent) {
            is CreateEmailTemplatesEvent.UpdateHtml -> {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(html = emailTemplatesEvent.html)
            }

            is CreateEmailTemplatesEvent.UpdateName -> {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(name = emailTemplatesEvent.name)
            }

            is CreateEmailTemplatesEvent.UpdateSubject -> {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(subject = emailTemplatesEvent.subject)
            }

            is CreateEmailTemplatesEvent.UpdateText -> {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(text = emailTemplatesEvent.text)
            }

            is CreateEmailTemplatesEvent.ChangeFormMode -> {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(isHTML = !_createTemplateForm.value.isHTML)
            }

            is CreateEmailTemplatesEvent.AddTemplate -> {
                addEmailTemplate()
            }

            is CreateEmailTemplatesEvent.AddOllamaEmail -> {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(responseNotBeingCreated = false)
                getOllamaEmail()
            }
        }
    }

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
    }

    private fun getOllamaEmail() {
        coroutineScope.launch {
            val subject = _createTemplateForm.value.subject
            val ollamaResponse = ollamaRepository.getEmail(subject)
            if (ollamaResponse.data != null) {
                _createTemplateForm.value =
                    _createTemplateForm.value.copy(html = ollamaResponse.data ?: "")
            } else {
                _apiCallResult.send(
                    ApiCallResult(
                        errorMessage = ollamaResponse.error,
                        successful = false
                    )
                )
            }
            _createTemplateForm.value =
                _createTemplateForm.value.copy(responseNotBeingCreated = true)
        }
    }

    private fun addEmailTemplate() {
        coroutineScope.launch {
            val result =
                templateRepository.createTemplate(createTemplateForm.value.toCreateTemplate())
            _apiCallResult.send(result)
        }
    }
}