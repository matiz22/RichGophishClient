package gophish.smtp.domain

import smtp.domain.model.CreateSmtp
import smtp.domain.model.Header


data class CreateSmtpForm(
    val fromAddress: String = "",
    val headers: List<CreateHeaderForm> = emptyList(),
    val host: String = "",
    val hostError: String? = null,
    val ignoreCertErrors: Boolean = true,
    val interfaceType: String = "SMTP",
    val name: String = "",
    val nameError: String? = null,
    val password: String = "",
    val username: String = ""
) {
    fun toCreatePage(): CreateSmtp {
        return CreateSmtp(
            fromAddress = fromAddress,
            headers = headers.map {
                Header(key = it.key, value = it.value)
            },
            host = host,
            name = name,
            ignoreCertErrors = ignoreCertErrors,
            username = username,
            password = password
        )
    }
}