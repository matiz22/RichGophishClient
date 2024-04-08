package template.data.remote

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
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
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
        }
        return if (request.status.isSuccess()) {
            println(request.bodyAsText())
            DataOrError<List<Template>>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<List<Template>>(error = error.message)
        }
    }

    suspend fun getTemplate(id:Long): DataOrError<Template> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
            url {
                path("$ROUTE$id")
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<Template>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<Template>(error = error.message)
        }
    }
    suspend fun createTemplate(createTemplate: CreateTemplate):ApiCallResult{
        val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
            setBody(createTemplate)
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
    }

    suspend fun modifyTemplate(createTemplate: CreateTemplate):ApiCallResult{
        val request = gophishHttpRequester.httpClient.put(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
            setBody(createTemplate)
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
    }
    suspend fun deleteTemplate(id: Long): ApiCallResult {
        val request = gophishHttpRequester.httpClient.delete(gophishHttpRequester.host){
            url{
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
    }
}