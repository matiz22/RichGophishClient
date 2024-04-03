package helper

import androidx.compose.runtime.mutableStateListOf

class ApplicationState {
    val htmlWindows = mutableStateListOf<HtmlViewerWindowState>()
    fun openNewEmailWindow(title: String, data:String) {
        htmlWindows += htmlViewerWindowState(
            title = title,
            data = data
        )
    }

    fun exit() {
        htmlWindows.clear()
    }

    private fun htmlViewerWindowState(
        title: String,
        data: String
    ) = HtmlViewerWindowState(
        title = title,
        data = data,
        openNewWindow = ::openNewEmailWindow,
        exit = ::exit,
        close = htmlWindows::remove
    )
}
