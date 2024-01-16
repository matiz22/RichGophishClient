package home.data

import com.matiz22.richgophishclient.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json


val mainClient = HttpClient {
    install(ContentNegotiation) { json() }
    defaultRequest {
        url {
            host = BuildKonfig.MAIN_API_HOST
        }
        contentType(ContentType.Application.Json)
        headers {
            append("X-Api-Key", BuildKonfig.MAIN_API_KEY)
        }
    }
}
