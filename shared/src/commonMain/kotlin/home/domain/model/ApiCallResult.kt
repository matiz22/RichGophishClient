package home.domain.model

data class ApiCallResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
