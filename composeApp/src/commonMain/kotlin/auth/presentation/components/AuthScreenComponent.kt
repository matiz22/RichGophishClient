package auth.presentation.components

import auth.presentation.events.AuthEvent
import auth.presentation.states.AuthFormState
import com.arkivanov.decompose.ComponentContext

class AuthScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit
) : ComponentContext by componentContext {

    private val authFormState = AuthFormState()
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> TODO()
            is AuthEvent.PasswordChanged -> TODO()
            AuthEvent.Submit -> onNavigate()
            is AuthEvent.isNewUserChanged -> TODO()
        }
    }
}
