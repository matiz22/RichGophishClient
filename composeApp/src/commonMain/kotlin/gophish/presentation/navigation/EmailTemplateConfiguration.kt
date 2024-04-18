package gophish.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class EmailTemplateConfiguration {
    @Serializable
    data object EmailListTemplateConfiguration : EmailTemplateConfiguration()

    @Serializable
    data object CreateEmailTemplateConfiguration : EmailTemplateConfiguration()
}