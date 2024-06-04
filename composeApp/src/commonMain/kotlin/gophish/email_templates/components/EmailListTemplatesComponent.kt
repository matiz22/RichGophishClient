package gophish.email_templates.components

import campaigns.domain.model.DataOrError
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.scope.Scope
import template.domain.model.Template
import template.domain.repository.TemplateRepository

class EmailListTemplatesComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }
    private val templateRepository by inject<TemplateRepository>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _templates: MutableStateFlow<DataOrError<List<Template>>> = MutableStateFlow(
        DataOrError()
    )
    val templates = _templates.asStateFlow()

    fun updateTemplates() {
        coroutineScope.launch {
            _templates.emit(templateRepository.getTemplates())
        }
    }
}