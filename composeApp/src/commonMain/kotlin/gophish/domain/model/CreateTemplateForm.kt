package gophish.domain.model

data class CreateTemplateForm(
    val responseNotBeingCreated: Boolean = true,
    val name: String = "",
    val nameError: String? = null,
    val subject: String = "",
    val subjectError: String? = null,
    val isHTML: Boolean = true,
    val html: String = "",
    val text: String = ""
)
