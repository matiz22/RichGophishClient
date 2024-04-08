package root.presentation

import applicationState
import config.presentation.navigation.ConfigScreensConfiguration

actual fun openHTML(title: String, data: String, navigate: ((ConfigScreensConfiguration)-> Unit)?) {
    applicationState.openNewHTMLWindow(title, data)
}