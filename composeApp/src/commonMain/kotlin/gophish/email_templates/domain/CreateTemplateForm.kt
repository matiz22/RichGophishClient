package gophish.email_templates.domain

import template.domain.model.CreateTemplate

data class CreateTemplateForm(
    val responseNotBeingCreated: Boolean = true,
    val name: String = "",
    val nameError: String? = null,
    val subject: String = "",
    val subjectError: String? = null,
    val isHTML: Boolean = true,
    val html: String = "",
    val text: String = ""
) {
    fun toCreateTemplate(): CreateTemplate {
        return if (isHTML) {
            CreateTemplate(
                name = name,
                subject = subject,
                html = html
            )
        } else {
            CreateTemplate(
                name = name,
                subject = subject,
                text = text
            )
        }
    }
}
