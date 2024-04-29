package auth.data.remote

import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import auth.domain.model.UserOrError
import com.matiz22.richgophishclient.shared.SharedRes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess


class UserApi(private val mainClient: HttpClient) {
    private val ROUTE = "/user"
    suspend fun fetchUserById(idCredentials: IdCredentials): UserOrError {
        return try {
            val request = mainClient.post("${ROUTE}/userById") {
                setBody(idCredentials)
            }
            if (request.status.isSuccess()) {
                UserOrError(user = request.body())
            } else {
                UserOrError(error = request.bodyAsText())
            }
        } catch (e: Exception) {
            UserOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun fetchUserByEmail(emailCredentials: EmailCredentials): UserOrError {
        return try {
            val request = mainClient.post("${ROUTE}/userByEmail") {
                setBody(emailCredentials)
            }
            if (request.status.isSuccess()) {
                UserOrError(user = request.body())
            } else {
                UserOrError(error = request.bodyAsText())
            }
        } catch (e: Exception) {
            UserOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun createUser(emailCredentials: EmailCredentials): UserOrError {
        return try {
            val request = mainClient.post("${ROUTE}/createUser") {
                setBody(emailCredentials)
            }
            if (request.status.isSuccess()) {
                UserOrError(user = request.body())
            } else {
                UserOrError(error = request.bodyAsText())
            }
        } catch (e: Exception) {
            UserOrError(error = SharedRes.string.connection_error)
        }
    }

    suspend fun deleteUser(id: Long): Boolean = try {
        mainClient.delete("${ROUTE}/deleteUser") {
            setBody(mapOf("id" to id))
        }.status.isSuccess()
    } catch (e: Exception) {
        false
    }


}