package ollama.data.repository

import campaigns.domain.model.DataOrError
import ollama.data.remote.OllamaApi
import ollama.domain.repository.OllamaRepository

class OllamaRepositoryImpl : OllamaRepository {
    private val ollamaApi = OllamaApi()
    override suspend fun getEmail(topic: String): DataOrError<String> {
        val dataFromOllama = ollamaApi.getEmail(topic)
        return if (dataFromOllama.data != null) {
            val data = extractHtmlContent(dataFromOllama.data.response)
            DataOrError(data = data)
        } else {
            DataOrError(error = dataFromOllama.error)
        }
    }

    private fun extractHtmlContent(inputString: String): String? {
        val startIndex = inputString.indexOfFirst { it == '<' }
        val endIndex = inputString.lastIndexOf("</html>")
        return inputString.substring(startIndex, endIndex) + "{{.Tracker}}</html>"
    }

}