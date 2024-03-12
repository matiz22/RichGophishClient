package configs.domain.model

data class ConfigsOrError(
    val configs: List<GophishConfig>? = null,
    val error: String? = null,
)