package config.presentation.events

import configs.domain.model.GophishConfig

sealed class ListOfConfigsEvent {
    data object AddConfig : ListOfConfigsEvent()
    data class HandleDialog(val gophishConfig: GophishConfig? = null) : ListOfConfigsEvent()
    data class DeleteConfig(val id: Long) : ListOfConfigsEvent()
    data object EditConfig : ListOfConfigsEvent()
    data class OnNameUpdate(val name: String) : ListOfConfigsEvent()
    data class OnUrlUpdate(val url: String) : ListOfConfigsEvent()
    data class OnApiKeyUpdate(val apiKey: String) : ListOfConfigsEvent()
}
