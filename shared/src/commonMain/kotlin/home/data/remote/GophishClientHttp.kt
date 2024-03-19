package home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.append
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

fun provideGophishHttpClient(host: String, apiKey: String) = HttpClient {
    install(ContentNegotiation) { json() }
    defaultRequest {
        url {
            this.host = host
        }
        contentType(ContentType.Application.Json)
        headers {
            append("Authorization", apiKey)
        }
    }
}