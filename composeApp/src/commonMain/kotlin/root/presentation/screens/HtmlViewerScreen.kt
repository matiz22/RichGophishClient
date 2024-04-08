package root.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState

@Composable
fun HtmlViewerScreen(webViewState: WebViewState) {
    WebView(
        modifier = Modifier.fillMaxSize(),
        state = webViewState
    )
}