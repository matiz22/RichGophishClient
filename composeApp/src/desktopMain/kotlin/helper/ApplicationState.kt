package helper

import androidx.compose.runtime.mutableStateListOf

class ApplicationState {
    val htmlWindows = mutableStateListOf<HtmlViewerWindowState>()
    fun openNewHTMLWindow(title: String, data:String) {
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
        openNewWindow = ::openNewHTMLWindow,
        exit = ::exit,
        close = htmlWindows::remove
    )
}
