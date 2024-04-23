package gophish.presentation.events

sealed class CreatePageEvent {
    data class UpdateName(val name: String) : CreatePageEvent()
    data class UpdateRedirectUrl(val redirectUrl: String) : CreatePageEvent()
    data class UpdateHtml(val html: String) : CreatePageEvent()
    data object UpdateCaptureCredential : CreatePageEvent()
    data object UpdateCapturePassword : CreatePageEvent()
}
