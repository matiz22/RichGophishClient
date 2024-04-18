package gophish.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.domain.model.CreateTemplateForm
import gophish.presentation.events.CreateEmailTemplatesEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ollama.domain.repository.OllamaRepository
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


class CreateEmailTemplateComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext  {
    override val scope: Scope by lazy { getKoin().getOrCreateScope("ollamaComponents", named("ollamaScope")) }
    private val ollamaRepository by inject<OllamaRepository>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    var createTemplateForm by mutableStateOf(CreateTemplateForm())

    fun onEvent(emailTemplatesEvent: CreateEmailTemplatesEvent) {
        when (emailTemplatesEvent) {
            is CreateEmailTemplatesEvent.UpdateHtml -> {
                createTemplateForm = createTemplateForm.copy(html = emailTemplatesEvent.html)
            }

            is CreateEmailTemplatesEvent.UpdateName -> {
                createTemplateForm = createTemplateForm.copy(name = emailTemplatesEvent.name)
            }

            is CreateEmailTemplatesEvent.UpdateSubject -> {
                createTemplateForm = createTemplateForm.copy(subject = emailTemplatesEvent.subject)
            }

            is CreateEmailTemplatesEvent.UpdateText -> {
                createTemplateForm = createTemplateForm.copy(text = emailTemplatesEvent.text)
            }

            is CreateEmailTemplatesEvent.ChangeFormMode -> {
                createTemplateForm = createTemplateForm.copy(isHTML = !createTemplateForm.isHTML)
            }

            is CreateEmailTemplatesEvent.AddTemplate -> {

            }

            is CreateEmailTemplatesEvent.AddOllamaEmail -> {
                getOllamaEmail()
            }
        }
    }

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
    }

    private fun getOllamaEmail(){
        coroutineScope.launch {
            val ollamaResponse = ollamaRepository.getEmail(createTemplateForm.subject)
            print(ollamaResponse.data.toString())
        }
    }
}