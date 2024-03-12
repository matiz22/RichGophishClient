package config.presentation.states

import configs.domain.model.CreateGophishConfig

data class ConfigFormState(
    val name: String = "",
    val nameError: String? = null,
    val url: String = "",
    val urlError: String? = null,
    val apiKey: String = "",
    val apiKeyError: String? = null
) {
    fun toCreateGophishConfig(userId: Long): CreateGophishConfig {
        return CreateGophishConfig(
            name = name,
            userId = userId,
            url = url,
            apiKey = apiKey
        )
    }
}