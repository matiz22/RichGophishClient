package template.data.remote

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
import com.matiz22.richgophishclient.shared.SharedRes
import home.domain.model.ApiCallResult
import home.domain.model.GophishCallResult
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.http.path
import template.domain.model.CreateTemplate
import template.domain.model.Template

class TemplateApi(
    private val gophishHttpRequester: GophishHttpRequester
) {
    private val ROUTE = "api/templates/"

    suspend fun getTemplates(): DataOrError<List<Template>> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<List<Template>>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<List<Template>>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun getTemplate(id: Long): DataOrError<Template> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<Template>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<Template>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun createTemplate(createTemplate: CreateTemplate): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
                setBody(createTemplate)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                val error: GophishCallResult = request.body()
                ApiCallResult(
                    successful = false,
                    errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }

    suspend fun modifyTemplate(createTemplate: CreateTemplate): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.put(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
                setBody(createTemplate)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                val error: GophishCallResult = request.body()
                ApiCallResult(
                    successful = false,
                    errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }

    suspend fun deleteTemplate(id: Long): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.delete(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
            }
            return if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                val error: GophishCallResult = request.body()
                ApiCallResult(
                    successful = false,
                    errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }
}