package configs.data.remote

import com.matiz22.richgophishclient.shared.SharedRes
import configs.domain.model.ConfigUserIdRequest
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
        return try {
            val request = mainClient.post("${ROUTE}/user") {
                setBody(ConfigUserIdRequest(id))
            }
            if (request.status.isSuccess()) {
                ConfigsOrError(configs = request.body())
            } else {
                ConfigsOrError(error = request.bodyAsText())
            }
        } catch (e: Exception) {
            ConfigsOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun deleteConfig(id: Long): ApiCallResult {
        return try {
            val request = mainClient.delete(ROUTE) {
                setBody(id)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                ApiCallResult(successful = false, errorMessage = SharedRes.string.deletion_error)
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }

    suspend fun createConfig(createGophishConfig: CreateGophishConfig): ApiCallResult {
        return try {
            val request = mainClient.post("${ROUTE}/post") {
                setBody(createGophishConfig)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                ApiCallResult(successful = false, errorMessage = SharedRes.string.creation_error)
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }

    suspend fun editConfig(editGophishConfig: EditGophishConfig): ApiCallResult {
        return try {
            val request = mainClient.put("${ROUTE}/edit") {
                setBody(editGophishConfig)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                ApiCallResult(successful = false, errorMessage = SharedRes.string.editing_error)
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }
}