package config.presentation.events

sealed class ListOfConfigsEvent {
    data object AddConfig : ListOfConfigsEvent()
}
