package ollama.data.remote

import campaigns.domain.model.DataOrError
import com.matiz22.richgophishclient.BuildKonfig
import com.matiz22.richgophishclient.shared.SharedRes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ollama.data.model.OllamaRequest
import ollama.data.model.OllamaResponse

class OllamaApi {

    private val ollamaRequester = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 1_000_000L
            socketTimeoutMillis = 1_000_000L
        }
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                host = BuildKonfig.OLLAMA_API_HOST
                path("api/generate")
            }
        }
    }

    suspend fun getEmail(topic: String): DataOrError<OllamaResponse> {
        val request = ollamaRequester.post {
            setBody(
                OllamaRequest(
                    model = BuildKonfig.OLLAMA_EMAIL_MODEL,
                    prompt = SharedRes.string.email_prompt.format(topic),
                )
            )
        }
        return if (request.status.isSuccess()) {
            DataOrError<OllamaResponse>(
                data = request.body()
            )
        } else {
            DataOrError(
                error = SharedRes.string.email_prompt_error
            )
        }
    }
}