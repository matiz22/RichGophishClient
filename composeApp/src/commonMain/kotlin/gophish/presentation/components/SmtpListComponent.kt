package gophish.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.domain.model.DataOrError
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.presentation.events.SmtpListEvent
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
import smtp.domain.model.Smtp
import smtp.domain.repository.SmtpRepository

class SmtpListComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }
    private val smtpRepository by inject<SmtpRepository>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _smtpList: MutableStateFlow<DataOrError<List<Smtp>>> =
        MutableStateFlow(
            DataOrError()
        )
    val smptList = _smtpList.asStateFlow()

    var pickedSmtp by mutableStateOf<Smtp?>(null)

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
        updateTemplates()
    }

    private fun updateTemplates() {
        coroutineScope.launch {
            _smtpList.emit(smtpRepository.getSmtpProfiles())
        }
    }

    fun onEvent(smtpListEvent: SmtpListEvent) {
        pickedSmtp = when (smtpListEvent) {
            is SmtpListEvent.PickSmtp -> {
                smtpListEvent.smtp
            }

            SmtpListEvent.UnPickSmtp -> {
                null
            }
        }
    }
}
