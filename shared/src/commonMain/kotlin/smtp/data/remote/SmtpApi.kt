package smtp.data.remote

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.Campaign
import campaigns.domain.model.DataOrError
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
    suspend fun getSmtpProfiles(): DataOrError<List<Smtp>>{
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path(ROUTE)
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<List<Smtp>>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<List<Smtp>>(error = error.message)
        }
    }
    suspend fun getSmtp(id: Long): DataOrError<Smtp>{
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path("$ROUTE$id")
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<Smtp>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<Smtp>(error = error.message)
        }
    }
    suspend fun createSmtp(createSmtp: CreateSmtp): ApiCallResult{
        val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
            setBody(createSmtp)
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
    suspend fun modifySmtp(id: Long, createSmtp: CreateSmtp): ApiCallResult{
        val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
            url {
                path("$ROUTE$id")
            }
            setBody(createSmtp)
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
    suspend fun deleteSmtp(id: Long): ApiCallResult{
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