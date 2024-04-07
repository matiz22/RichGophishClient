package root.presentation

import applicationState

actual fun openHTML(title:String, data:String) {
    applicationState.openNewHTMLWindow(title, data)
}