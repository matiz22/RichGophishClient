package config.presentation.states

import config.presentation.events.ConfigDialogMode
import configs.domain.model.CreateGophishConfig
import configs.domain.model.EditGophishConfig

data class ConfigFormState(
    val isShown: Boolean = false,
    val id: Long? = null,
    val name: String = "",
    val nameError: String? = null,
    val url: String = "",
    val urlError: String? = null,
    val apiKey: String = "",
    val apiKeyError: String? = null,
    val mode: ConfigDialogMode = ConfigDialogMode.CREATE
) {
    fun toCreateGophishConfig(userId: Long): CreateGophishConfig {
        return CreateGophishConfig(
            name = name,
            userId = userId,
            url = url,
            apiKey = apiKey
        )
    }

    fun toEditGophishConfig(id: Long, userId: Long): EditGophishConfig {
        return EditGophishConfig(
            id = id,
            userId = userId,
            name = name,
            url = url,
            apiKey = apiKey
        )
    }
}