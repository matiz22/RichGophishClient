package auth.presentation.components

import auth.presentation.events.AuthEvent
import auth.presentation.states.AuthFormState
import com.arkivanov.decompose.ComponentContext
import home.di.ValidatorsComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit
) : ComponentContext by componentContext {

    private val validateEmail = ValidatorsComponent().emailValidator

    private val _authFormState = MutableStateFlow(
        AuthFormState()
    )
    val authFormState = _authFormState.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> TODO()
            is AuthEvent.PasswordChanged -> TODO()
            AuthEvent.Submit -> onNavigate()
            is AuthEvent.isNewUserChanged -> TODO()
        }
    }

    private fun validateForm() {

    }
}
