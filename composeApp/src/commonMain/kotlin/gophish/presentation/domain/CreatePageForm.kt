package gophish.presentation.domain

data class CreatePageForm(
    val name: String = "",
    val html: String = "",
    val captureCredentials: Boolean = false,
    val capturePassword: Boolean = false,
    val redirectUrl: String = ""
)