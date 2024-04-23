package group.data.remote

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
import group.domain.model.CreateGroup
import group.domain.model.Group
import group.domain.model.ModifyGroup
import home.domain.model.ApiCallResult
import home.domain.model.GophishCallResult
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import io.ktor.http.path

class UserGroupApi(private val gophishHttpRequester: GophishHttpRequester) {
    private val ROUTE = "api/groups/"
    suspend fun getGroups(): DataOrError<List<Group>> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<List<Group>>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<List<Group>>(error = error.message)
        }
    }

    suspend fun getGroup(id: Long): DataOrError<Group> {
        val request = gophishHttpRequester.httpClient.get(gophishHttpRequester.host) {
            url {
                path("$ROUTE$id")
            }
        }
        return if (request.status.isSuccess()) {
            DataOrError<Group>(data = request.body())
        } else {
            val error: GophishCallResult = request.body()
            DataOrError<Group>(error = error.message)
        }
    }

    suspend fun createGroup(createGroup: CreateGroup): ApiCallResult {
        val request = gophishHttpRequester.httpClient.post(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
            setBody(createGroup)
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

    suspend fun modifyGroup(modifyGroup: ModifyGroup): ApiCallResult {
        val request = gophishHttpRequester.httpClient.put(gophishHttpRequester.host) {
            url {
                path(ROUTE)
            }
            setBody(modifyGroup)
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

    suspend fun deleteGroup(id: Long): ApiCallResult {
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
    }
}