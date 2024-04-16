package gophish.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.domain.model.DataOrError
import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.domain.model.CreateTemplateForm
import gophish.presentation.events.EmailTemplatesEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.scope.Scope
import template.domain.model.Template
import template.domain.repository.TemplateRepository

class EmailTemplatesComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }
    private val templateRepository by inject<TemplateRepository>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _templates: MutableStateFlow<DataOrError<List<Template>>> = MutableStateFlow(
        DataOrError()
    )
    val templates = _templates.asStateFlow()

    var createTemplateForm by mutableStateOf(CreateTemplateForm())

    fun onEvent(emailTemplatesEvent: EmailTemplatesEvent) {
        when (emailTemplatesEvent) {
            is EmailTemplatesEvent.UpdateHtml -> {
                createTemplateForm = createTemplateForm.copy(html = emailTemplatesEvent.html)
            }

            is EmailTemplatesEvent.UpdateName -> {
                createTemplateForm = createTemplateForm.copy(name = emailTemplatesEvent.name)
            }

            is EmailTemplatesEvent.UpdateSubject -> {
                createTemplateForm = createTemplateForm.copy(subject = emailTemplatesEvent.subject)
            }

            is EmailTemplatesEvent.UpdateText -> {
                createTemplateForm = createTemplateForm.copy(text = emailTemplatesEvent.text)
            }

            is EmailTemplatesEvent.ChangeFormVisibility -> {
                createTemplateForm =
                    createTemplateForm.copy(isShownDialog = !createTemplateForm.isShownDialog)
            }

            is EmailTemplatesEvent.ChangeFormMode -> {
                createTemplateForm = createTemplateForm.copy(isHTML = !createTemplateForm.isHTML)
            }
        }
    }

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
        updateTemplates()
    }

    private fun updateTemplates() {
        coroutineScope.launch {
            _templates.emit(templateRepository.getTemplates())
        }
    }

}