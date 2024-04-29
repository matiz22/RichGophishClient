package gophish.email_templates.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class EmailTemplateConfiguration {
    @Serializable
    data object EmailListTemplateConfiguration : EmailTemplateConfiguration()

    @Serializable
    data object CreateEmailTemplateConfiguration : EmailTemplateConfiguration()
}