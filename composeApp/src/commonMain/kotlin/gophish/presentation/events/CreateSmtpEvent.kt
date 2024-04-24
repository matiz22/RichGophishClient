package gophish.presentation.events

sealed class CreateSmtpEvent {
    data class UpdateName(val name: String) : CreateSmtpEvent()
    data class UpdateFromAddress(val fromAddress: String) : CreateSmtpEvent()
    data class UpdateHost(val host: String) : CreateSmtpEvent()
    data class UpdateUsername(val username: String) : CreateSmtpEvent()
    data class UpdatePassword(val password: String) : CreateSmtpEvent()
    data class UpdateHeaderKey(val key: String) : CreateSmtpEvent()
    data class UpdateHeaderValue(val value: String) : CreateSmtpEvent()
    data object Submit : CreateSmtpEvent()
}
