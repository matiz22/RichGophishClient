package campaigns.domain.model

data class DataOrError<T>(
    val data: T? = null,
    val error: String? = null
)
