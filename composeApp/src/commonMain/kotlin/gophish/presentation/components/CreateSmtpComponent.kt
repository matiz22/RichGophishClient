package gophish.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import gophish.presentation.domain.CreateHeaderForm
import gophish.presentation.domain.CreateSmtpForm
import gophish.presentation.events.CreateSmtpEvent
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.scope.Scope
import smtp.domain.repository.SmtpRepository

class CreateSmtpComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _createSmtpForm = mutableStateOf(CreateSmtpForm())
    val createSmtpForm: State<CreateSmtpForm> = _createSmtpForm

    private val _createHeaderForm = mutableStateOf(CreateHeaderForm())
    val createHeaderForm: State<CreateHeaderForm> = _createHeaderForm

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    private val smtpRepository by inject<SmtpRepository>()

    fun onEvent(createSmtpEvent: CreateSmtpEvent) {
        when (createSmtpEvent) {
            is CreateSmtpEvent.Submit -> TODO()
            is CreateSmtpEvent.UpdateName -> {
                _createSmtpForm.value = _createSmtpForm.value.copy(name = createSmtpEvent.name)
            }

            is CreateSmtpEvent.UpdateFromAddress -> {
                _createSmtpForm.value = _createSmtpForm.value.copy(fromAddress = createSmtpEvent.fromAddress)
            }
            is CreateSmtpEvent.UpdateHeaderKey -> {

            }
            is CreateSmtpEvent.UpdateHeaderValue -> TODO()
            is CreateSmtpEvent.UpdateHost -> TODO()
            is CreateSmtpEvent.UpdatePassword -> TODO()
            is CreateSmtpEvent.UpdateUsername -> TODO()
        }
    }

}
