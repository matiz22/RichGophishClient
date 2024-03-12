package configs.data.remote

import com.matiz22.richgophishclient.shared.SharedRes
import configs.domain.model.ConfigsOrError
import configs.domain.model.CreateGophishConfig
import configs.domain.model.EditGophishConfig
import home.domain.model.ApiCallResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ConfigApi(private val mainClient: HttpClient) {
    private val ROUTE = "/configs"

    suspend fun fetchConfigs(id: Long): ConfigsOrError {
        val request = mainClient.get("${ROUTE}/user") {
            contentType(ContentType.Text.Plain)
            setBody(id.toString())
        }
        println(request.bodyAsText())
        println(request.status)
        return if (request.status.isSuccess()) {
            ConfigsOrError(configs = request.body())
        } else {
            ConfigsOrError(error = request.bodyAsText())
        }
    }

    suspend fun deleteConfig(id: Long): ApiCallResult {
        val request = mainClient.delete(ROUTE) {
            setBody(id)
        }
        return if (request.status.isSuccess()) {
            ApiCallResult(successful = true)
        } else {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.deletion_error)
        }
    }

    suspend fun createConfig(createGophishConfig: CreateGophishConfig): ApiCallResult {
        val request = mainClient.post("${ROUTE}/post") {
            setBody(createGophishConfig)
        }
        return if (request.status.isSuccess()) {
            ApiCallResult(successful = true)
        } else {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.creation_error)
        }
    }

    suspend fun editConfig(editGophishConfig: EditGophishConfig): ApiCallResult {
        val request = mainClient.put("${ROUTE}/edit") {
            setBody(editGophishConfig)
        }
        return if (request.status.isSuccess()) {
            ApiCallResult(successful = true)
        } else {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.editing_error)
        }
    }
}