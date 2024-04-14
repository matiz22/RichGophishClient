package gophish.events

sealed class EmailTemplatesEvent {
    data class UpdateName(val name: String) : EmailTemplatesEvent()
    data class UpdateSubject(val subject: String) : EmailTemplatesEvent()
    data class UpdateText(val text: String) : EmailTemplatesEvent()
    data class UpdateHtml(val html: String) : EmailTemplatesEvent()
    data object ChangeFormVisibility : EmailTemplatesEvent()
}
