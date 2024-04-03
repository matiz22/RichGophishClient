package helper

import androidx.compose.runtime.Composable

class HtmlViewerWindowState(
    val title: String,
    val data: String,
    val openNewWindow: (String, String) -> Unit,
    val exit: () -> Unit,
    private val close: (HtmlViewerWindowState) -> Unit
) {
    fun close() = close(this)
}
