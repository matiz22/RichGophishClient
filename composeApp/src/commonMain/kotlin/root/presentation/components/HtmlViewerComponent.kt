package root.presentation.components

import com.arkivanov.decompose.ComponentContext

class HtmlViewerComponent(
    val componentContext: ComponentContext,
    val title: String,
    val data: String
    ) : ComponentContext by componentContext