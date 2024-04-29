package gophish.smtp.events

import gophish.smtp.domain.CreateHeaderForm

sealed class CreateSmtpEvent {
    data class UpdateName(val name: String) : CreateSmtpEvent()
    data class UpdateFromAddress(val fromAddress: String) : CreateSmtpEvent()
    data class UpdateHost(val host: String) : CreateSmtpEvent()
    data class UpdateUsername(val username: String) : CreateSmtpEvent()
    data class UpdatePassword(val password: String) : CreateSmtpEvent()
    data class UpdateHeaderKey(val key: String) : CreateSmtpEvent()
    data class UpdateHeaderValue(val value: String) : CreateSmtpEvent()
    data class DeleteHeader(val header: CreateHeaderForm) : CreateSmtpEvent()

    data object AddHeader : CreateSmtpEvent()
    data object Submit : CreateSmtpEvent()
}
