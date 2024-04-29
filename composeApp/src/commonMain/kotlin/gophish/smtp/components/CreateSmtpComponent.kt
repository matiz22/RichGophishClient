package gophish.smtp.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.smtp.domain.CreateHeaderForm
import gophish.smtp.domain.CreateSmtpForm
import gophish.smtp.events.CreateSmtpEvent
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

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
    }

    fun onEvent(createSmtpEvent: CreateSmtpEvent) {
        when (createSmtpEvent) {
            is CreateSmtpEvent.Submit -> {
                submitSmtp()
            }

            is CreateSmtpEvent.UpdateName -> {
                _createSmtpForm.value = _createSmtpForm.value.copy(name = createSmtpEvent.name)
            }

            is CreateSmtpEvent.UpdateFromAddress -> {
                _createSmtpForm.value =
                    _createSmtpForm.value.copy(fromAddress = createSmtpEvent.fromAddress)
            }

            is CreateSmtpEvent.UpdateHeaderKey -> {
                _createHeaderForm.value = _createHeaderForm.value.copy(key = createSmtpEvent.key)
            }

            is CreateSmtpEvent.UpdateHeaderValue -> {
                _createHeaderForm.value =
                    _createHeaderForm.value.copy(value = createSmtpEvent.value)
            }

            is CreateSmtpEvent.UpdateHost -> {
                _createSmtpForm.value = _createSmtpForm.value.copy(host = createSmtpEvent.host)
            }

            is CreateSmtpEvent.UpdatePassword -> {
                _createSmtpForm.value =
                    _createSmtpForm.value.copy(password = createSmtpEvent.password)
            }

            is CreateSmtpEvent.UpdateUsername -> {
                _createSmtpForm.value =
                    _createSmtpForm.value.copy(username = createSmtpEvent.username)
            }

            is CreateSmtpEvent.AddHeader -> {
                if (_createHeaderForm.value.key != "" && _createHeaderForm.value.value != "") {
                    _createSmtpForm.value =
                        _createSmtpForm.value.copy(headers = _createSmtpForm.value.headers + _createHeaderForm.value)
                }
            }

            is CreateSmtpEvent.DeleteHeader -> {
                if (_createHeaderForm.value.key != "" && _createHeaderForm.value.value != "") {
                    _createSmtpForm.value =
                        _createSmtpForm.value.copy(headers = _createSmtpForm.value.headers - _createHeaderForm.value)
                }
            }
        }
    }

    private fun submitSmtp() {
        coroutineScope.launch {
            val result =
                smtpRepository.createSmtp(_createSmtpForm.value.toCreatePage())
            withContext(Dispatchers.Main){
                _apiCallResult.send(result)
            }
        }
    }
}
