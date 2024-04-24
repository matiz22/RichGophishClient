package gophish.presentation.domain


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
)