package ollama.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OllamaResponse(
    val model: String,
    @SerialName("created_at")
    val createdAt: String,
    val response: String,
    val done: Boolean,
    val context: List<Int>,
    @SerialName("total_duration")
    val totalDuration: Long,
    @SerialName("load_duration")
    val loadDuration: Long,
//    @SerialName("prompt_eval_count")
//    val promptEvalCount: Int,
    @SerialName("prompt_eval_duration")
    val promptEvalDuration: Int,
    @SerialName("eval_count")
    val evalCount: Int,
    @SerialName("eval_duration")
    val evalDuration: Long
)