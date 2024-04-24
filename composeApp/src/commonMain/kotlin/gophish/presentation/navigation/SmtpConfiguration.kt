package gophish.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SmtpConfiguration {
    @Serializable
    data object SmtpListConfiguration : SmtpConfiguration()

    @Serializable
    data object CreateSmtpConfiguration : SmtpConfiguration()
}
