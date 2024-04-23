package gophish.presentation.components

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
import page.domain.model.Page
import page.domain.repository.PagesRepository


class PagesListComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }
    private val pagesRepository by inject<PagesRepository>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _pages: MutableStateFlow<DataOrError<List<Page>>> = MutableStateFlow(
        DataOrError()
    )
    val pages = _pages.asStateFlow()

    init {
        updatePages()
    }

    private fun updatePages() {
        coroutineScope.launch {
            _pages.emit(pagesRepository.getPages())
        }
    }
}
