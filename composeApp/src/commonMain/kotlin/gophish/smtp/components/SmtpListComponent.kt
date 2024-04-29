package gophish.smtp.components

import campaigns.domain.model.DataOrError
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.smtp.events.SmtpListEvent
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val smtpList = _smtpList.asStateFlow()

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
    }

    fun updateSmtp() {
        coroutineScope.launch {
            _smtpList.emit(smtpRepository.getSmtpProfiles())
        }
    }

    fun onEvent(smtpListEvent: SmtpListEvent) {
        when (smtpListEvent) {
            is SmtpListEvent.DeleteSmtp -> {
                coroutineScope.launch {
                    val result = deleteSmtp(smtpListEvent.smtp).await()
                    if (result.successful) {
                        updateSmtp()
                    }
                }
            }
        }
    }

    private fun deleteSmtp(smtp: Smtp): Deferred<ApiCallResult> {
        return coroutineScope.async {
            val result = smtpRepository.deleteSmtp(smtp.id)
            withContext(Dispatchers.Main) {
                _apiCallResult.send(result)
            }
            result
        }
    }
}
