package auth.presentation.components

import auth.presentation.events.AuthEvent
import auth.presentation.states.AuthFormState
import com.arkivanov.decompose.ComponentContext
import home.di.ValidatorsComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthScreenComponent(
    componentContext: ComponentContext, private val onNavigate: () -> Unit
) : ComponentContext by componentContext {

    private val validateEmail = ValidatorsComponent().emailValidator
    private val validatePassword = ValidatorsComponent().passwordValidator

    private val _authFormState = MutableStateFlow(
        AuthFormState()
    )
    val authFormState = _authFormState.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                _authFormState.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is AuthEvent.PasswordChanged -> {
                _authFormState.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is AuthEvent.isNewUserChanged -> {
                _authFormState.update {
                    it.copy(
                        isNewUser = event.isNewUserBool
                    )
                }
            }

            is AuthEvent.Submit -> validateForm()
        }
    }

    private fun validateForm() {
        val emailResult = validateEmail.execute(authFormState.value.email)
        val passwordResult = validatePassword.execute(authFormState.value.password)

        val isCorrect = listOf(
            emailResult,
            passwordResult
        ).all {
            it.successful
        }

        if (!isCorrect) {
            _authFormState.update {
                it.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
            }
        }
    }
}
