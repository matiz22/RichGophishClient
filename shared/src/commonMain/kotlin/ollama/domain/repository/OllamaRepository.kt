package ollama.domain.repository

import campaigns.domain.model.DataOrError

interface OllamaRepository {
    suspend fun getEmail(topic: String): DataOrError<String>
    suspend fun getPage(topic: String):DataOrError<String>
}