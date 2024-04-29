package page.data.remote

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
import page.domain.model.CreatePage
import page.domain.model.Page

class PagesApi(
    private val gophishHttpRequester: GophishHttpRequester
) {
    private val ROUTE = "api/pages/"

    suspend fun getPages(): DataOrError<List<Page>> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<List<Page>>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<List<Page>>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun getPage(id: Long): DataOrError<Page> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<Page>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<Page>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun createPage(createPage: CreatePage): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
                setBody(createPage)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                val error: GophishCallResult = request.body()
                ApiCallResult(
                    successful = false, errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }

    suspend fun modifyPage(modifyPage: CreatePage): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.put(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
                setBody(modifyPage)
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                val error: GophishCallResult = request.body()
                ApiCallResult(
                    successful = false, errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }

    suspend fun deletePage(id: Long): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.delete(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
            }
            if (request.status.isSuccess()) {
                ApiCallResult(successful = true)
            } else {
                val error: GophishCallResult = request.body()
                ApiCallResult(
                    successful = false, errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }
}