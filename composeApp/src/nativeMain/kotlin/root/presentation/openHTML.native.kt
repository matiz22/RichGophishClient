package root.presentation

import config.presentation.navigation.ConfigScreensConfiguration

actual fun openHTML(title: String, data: String, navigate: ((ConfigScreensConfiguration)-> Unit)?) {
    if(navigate != null){
        navigate(ConfigScreensConfiguration.HtmlViewer(title, data))
    }
}