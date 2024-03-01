package auth.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import auth.domain.model.User
import auth.presentation.events.AuthEvent
import auth.presentation.states.AuthFormState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnPause
import home.di.UserKoinComponent
import home.di.ValidatorsComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class AuthScreenComponent(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val navigateToConfig: (User) -> Unit
) : ComponentContext by componentContext {


    private val validateEmail = ValidatorsComponent().emailValidator
    private val validatePassword = ValidatorsComponent().passwordValidator

    private val userRepository = UserKoinComponent().userRepository

    var authFormState by mutableStateOf(AuthFormState())

    private val scope = CoroutineScope(mainCoroutineContext + SupervisorJob())

    init {
        lifecycle.doOnDestroy { scope.cancel() }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                authFormState = authFormState.copy(email = event.email)
            }

            is AuthEvent.PasswordChanged -> {
                authFormState = authFormState.copy(password = event.password)
            }

            is AuthEvent.IsNewUserChanged -> {
                authFormState = authFormState.copy(isNewUser = event.isNewUserBool)
            }

            is AuthEvent.Submit -> validateForm()
        }
    }

    private fun validateForm() {
        val emailResult = validateEmail.execute(authFormState.email)
        val passwordResult = validatePassword.execute(authFormState.password)

        val isCorrect = listOf(
            emailResult,
            passwordResult
        ).all {
            it.successful
        }

        if (!isCorrect) {
            authFormState = authFormState.copy(emailError = emailResult.errorMessage)
            authFormState = authFormState.copy(passwordError = passwordResult.errorMessage)
            return
        } else {
            scope.launch(Dispatchers.IO) {
                val result = if (authFormState.isNewUser) {
                    userRepository.createUser(authFormState.toEmailCredentials())
                } else {
                    userRepository.getUserByEmail(authFormState.toEmailCredentials())
                }
                if (result.user != null) {
                    withContext(Dispatchers.Main) {
                        navigateToConfig(result.user!!)
                    }
                } else {
                    authFormState =
                        authFormState.copy(otherErrors = result.error ?: "Something went wrong")
                }
            }
        }
    }
}
