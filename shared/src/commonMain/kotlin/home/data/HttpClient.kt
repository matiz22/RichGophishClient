package home.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json


val client = HttpClient {
    install(ContentNegotiation) { json()}
    defaultRequest {
        url{
            host = "richgophishapi.azurewebsites.net"
        }
    }
    co
}
