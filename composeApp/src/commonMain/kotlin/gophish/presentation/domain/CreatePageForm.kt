package gophish.presentation.domain

import page.domain.model.CreatePage

data class CreatePageForm(
    val responseNotBeingCreated: Boolean = true,
    val name: String = "",
    val nameError: String? = null,
    val html: String = "",
    val captureCredentials: Boolean = false,
    val capturePassword: Boolean = false,
    val redirectUrl: String = "",
    val redirectUrlError: String? = null
) {
    fun toCreatePage(): CreatePage {
        return CreatePage(
            name = name,
            html = html,
            captureCredentials = captureCredentials,
            capturePasswords = capturePassword,
            redirectUrl = redirectUrl
        )
    }
}