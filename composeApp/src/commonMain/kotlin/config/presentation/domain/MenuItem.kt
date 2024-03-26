package config.presentation.domain

data class MenuItem(
    val title: String,
    val action: () -> Unit
)