package gophish.presentation.events

sealed class CreateEmailTemplatesEvent {
    data class UpdateName(val name: String) : CreateEmailTemplatesEvent()
    data class UpdateSubject(val subject: String) : CreateEmailTemplatesEvent()
    data class UpdateText(val text: String) : CreateEmailTemplatesEvent()
    data class UpdateHtml(val html: String) : CreateEmailTemplatesEvent()
    data object ChangeFormMode : CreateEmailTemplatesEvent()
    data object AddTemplate : CreateEmailTemplatesEvent()
    data object AddOllamaEmail : CreateEmailTemplatesEvent()
}
