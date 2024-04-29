package smtp.data.remote

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.Campaign
import campaigns.domain.model.DataOrError
import com.matiz22.richgophishclient.shared.SharedRes
import home.domain.model.ApiCallResult
import home.domain.model.GophishCallResult
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import io.ktor.http.path
import smtp.domain.model.CreateSmtp
import smtp.domain.model.Smtp

class SmtpApi(private val gophishHttpRequester: GophishHttpRequester) {
    private val ROUTE = "api/smtp/"
    suspend fun getSmtpProfiles(): DataOrError<List<Smtp>> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<List<Smtp>>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<List<Smtp>>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError<List<Smtp>>(error = SharedRes.string.connection_error)
        }
    }

    suspend fun getSmtp(id: Long): DataOrError<Smtp> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<Smtp>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<Smtp>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun createSmtp(createSmtp: CreateSmtp): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
                setBody(createSmtp)
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

    suspend fun modifySmtp(id: Long, createSmtp: CreateSmtp): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
                setBody(createSmtp)
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

    suspend fun deleteSmtp(id: Long): ApiCallResult {
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
                    successful = false,
                    errorMessage = error.message
                )
            }
        } catch (e: Exception) {
            ApiCallResult(successful = false, errorMessage = SharedRes.string.connection_error)
        }
    }
}