package gophish.smtp.events

import smtp.domain.model.Smtp

sealed class SmtpListEvent {
    data class DeleteSmtp(val smtp: Smtp) : SmtpListEvent()
}