package root.presentation

import applicationState

actual fun openHTML(title: String, data: String, navigate: () -> Unit) {
    applicationState.openNewHTMLWindow(title, data)
}