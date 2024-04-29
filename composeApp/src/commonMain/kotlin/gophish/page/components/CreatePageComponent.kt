package gophish.page.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.page.domain.CreatePageForm
import gophish.page.events.CreatePageEvent
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ollama.domain.repository.OllamaRepository
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import page.domain.repository.PagesRepository


class CreatePageComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy {
        getKoin().getOrCreateScope(
            "ollamaComponents", named("ollamaScope")
        )
    }
    private val ollamaRepository by inject<OllamaRepository>()

    private val pagesScope: Scope by lazy { getKoin().getScope("gophishComponents") }
    private val pagesRepository = pagesScope.get<PagesRepository>()

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    private val _createPageForm = mutableStateOf(CreatePageForm())
    val createPageForm: State<CreatePageForm> = _createPageForm

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
    }

    fun onEvent(createPageEvent: CreatePageEvent) {
        when (createPageEvent) {
            is CreatePageEvent.UpdateCaptureCredential -> {
                _createPageForm.value =
                    _createPageForm.value.copy(captureCredentials = !_createPageForm.value.captureCredentials)
            }

            is CreatePageEvent.UpdateCapturePassword -> {
                _createPageForm.value =
                    _createPageForm.value.copy(capturePassword = !_createPageForm.value.capturePassword)

            }

            is CreatePageEvent.UpdateHtml -> {
                _createPageForm.value = _createPageForm.value.copy(html = createPageEvent.html)
            }

            is CreatePageEvent.UpdateName -> {
                _createPageForm.value = _createPageForm.value.copy(name = createPageEvent.name)
            }

            is CreatePageEvent.UpdateRedirectUrl -> {
                _createPageForm.value =
                    _createPageForm.value.copy(redirectUrl = createPageEvent.redirectUrl)
            }

            is CreatePageEvent.AddPage -> {
                addEmailTemplate()
            }

            is CreatePageEvent.GeneratePage -> {
                _createPageForm.value =
                    _createPageForm.value.copy(responseNotBeingCreated = false)
                getOllamaPage()
            }
        }
    }

    private fun getOllamaPage() {
        coroutineScope.launch {
            val subject = _createPageForm.value.name
            val ollamaResponse = ollamaRepository.getPage(subject)
            if (ollamaResponse.data != null) {
                _createPageForm.value =
                    _createPageForm.value.copy(html = ollamaResponse.data ?: "")
            } else {
                _apiCallResult.send(
                    ApiCallResult(
                        errorMessage = ollamaResponse.error,
                        successful = false
                    )
                )
            }
            _createPageForm.value =
                _createPageForm.value.copy(responseNotBeingCreated = true)
        }
    }

    private fun addEmailTemplate() {
        coroutineScope.launch {
            val result =
                pagesRepository.createPage(createPageForm.value.toCreatePage())
            withContext(Dispatchers.Main){
                _apiCallResult.send(result)
            }
        }
    }
}
