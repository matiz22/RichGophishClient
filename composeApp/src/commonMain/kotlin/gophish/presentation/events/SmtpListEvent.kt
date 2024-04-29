package gophish.presentation.events

import group.domain.model.Group
import smtp.domain.model.Smtp

sealed class SmtpListEvent {
    data class PickSmtp(val smtp: Smtp): SmtpListEvent()
    data object UnPickSmtp :SmtpListEvent()
}