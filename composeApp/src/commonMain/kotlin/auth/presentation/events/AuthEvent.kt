package auth.presentation.events

sealed class AuthEvent {
    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data class isNewUserChanged(val isNewUserBool: Boolean) : AuthEvent()
    data object Submit : AuthEvent()
}