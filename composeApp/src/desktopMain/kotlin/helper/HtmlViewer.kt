package helper

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData

@Composable
fun ApplicationScope.HtmlViewerWindow(windowState: HtmlViewerWindowState) {
    Window(
        title = "Rgc",
        onCloseRequest = { windowState.close() }) {
        HtmlViewer(windowState.data)
    }
}

@Composable
fun HtmlViewer(data: String) {
    val state = rememberWebViewStateWithHTMLData(data = data)
    WebView(
        modifier = Modifier.fillMaxSize(),
        state = state
    )
}