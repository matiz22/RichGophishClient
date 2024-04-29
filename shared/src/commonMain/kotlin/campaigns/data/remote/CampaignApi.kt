package campaigns.data.remote


import auth.domain.model.UserOrError
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignResult
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.CreateCampaign
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

class CampaignApi(
    private val gophishHttpRequester: GophishHttpRequester
) {
    private val ROUTE = "api/campaigns/"


    suspend fun getCampaigns(): DataOrError<List<Campaign>> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<List<Campaign>>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<List<Campaign>>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }

    }

    suspend fun getCampaign(id: Long): DataOrError<Campaign> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id")
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<Campaign>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<Campaign>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }


    }

    suspend fun createCampaign(createCampaign: CreateCampaign): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
                url {
                    path(ROUTE)
                }
                setBody(createCampaign)
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

    suspend fun getCampaignResult(id: Long): DataOrError<CampaignResult> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id/results")
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<CampaignResult>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<CampaignResult>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun getCampaignSummary(id: Long): DataOrError<CampaignSummary> {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id/summary")
                }
            }
            if (request.status.isSuccess()) {
                DataOrError<CampaignSummary>(data = request.body())
            } else {
                val error: GophishCallResult = request.body()
                DataOrError<CampaignSummary>(error = error.message)
            }
        } catch (e: Exception) {
            DataOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun deleteCampaign(id: Long): ApiCallResult {
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

    suspend fun completeCampaign(id: Long): ApiCallResult {
        return try {
            val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
                url {
                    path("$ROUTE$id/complete")
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
