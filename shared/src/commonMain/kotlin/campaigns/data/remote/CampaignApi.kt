package campaigns.data.remote


import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignResult
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.CreateCampaign
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

class CampaignApi(
    private val gophishHttpRequester: GophishHttpRequester
) {
    private val ROUTE = "api/campaigns/"


    suspend fun getCampaigns(): DataOrError<List<Campaign>> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path(ROUTE)
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<List<Campaign>>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<List<Campaign>>(error = error.message)
        }
    }

    suspend fun getCampaign(id: Long): DataOrError<Campaign> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path("$ROUTE$id")
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<Campaign>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<Campaign>(error = error.message)
        }
    }

    suspend fun createCampaign(createCampaign: CreateCampaign): ApiCallResult {
        val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
            setBody(createCampaign)
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

    suspend fun getCampaignResult(id: Long): DataOrError<CampaignResult> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path("$ROUTE$id/results")
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<CampaignResult>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<CampaignResult>(error = error.message)
        }
    }

    suspend fun getCampaignSummary(id: Long): DataOrError<CampaignSummary> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path("$ROUTE$id/summary")
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<CampaignSummary>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<CampaignSummary>(error = error.message)
        }
    }

    suspend fun deleteCampaign(id: Long): ApiCallResult {
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

    suspend fun completeCampaign(id: Long): ApiCallResult {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host){
            url{
                path("$ROUTE$id/complete")
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
