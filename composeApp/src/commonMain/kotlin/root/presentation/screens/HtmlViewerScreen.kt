package root.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HtmlViewerScreen(title: String, webViewState: WebViewState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                }
            )
        }
    ) {
        WebView(
            modifier = Modifier.fillMaxSize().padding(it).background(color = Color.White),
            state = webViewState
        )
    }
}