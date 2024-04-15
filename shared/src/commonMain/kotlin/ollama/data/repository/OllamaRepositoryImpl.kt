package ollama.data.repository

import campaigns.domain.model.DataOrError
import ollama.data.remote.OllamaApi
import ollama.domain.repository.OllamaRepository

class OllamaRepositoryImpl : OllamaRepository {
    private val ollamaApi = OllamaApi()
    override suspend fun getEmail(topic: String): DataOrError<String> {
        val dataFromOllama = ollamaApi.getEmail(topic)
        return if (dataFromOllama.data != null) {
            DataOrError(data = dataFromOllama.data.response)
        } else {
            DataOrError(error = dataFromOllama.error)
        }
    }
}