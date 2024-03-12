package auth.data.remote

import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import auth.domain.model.UserOrError
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
        val request = mainClient.post("${ROUTE}/userById") {
            setBody(idCredentials)
        }
        return if (request.status.isSuccess()) {
            UserOrError(user = request.body())
        } else {
            UserOrError(error = request.bodyAsText())
        }
    }

    suspend fun fetchUserByEmail(emailCredentials: EmailCredentials): UserOrError {
        val request = mainClient.post("${ROUTE}/userByEmail") {
            setBody(emailCredentials)
        }
        return if (request.status.isSuccess()) {
            UserOrError(user = request.body())
        } else {
            UserOrError(error = request.bodyAsText())
        }
    }

    suspend fun createUser(emailCredentials: EmailCredentials): UserOrError {
        val request = mainClient.post("${ROUTE}/createUser") {
            setBody(emailCredentials)
        }
        return if (request.status.isSuccess()) {
            UserOrError(user = request.body())
        } else {
            UserOrError(error = request.bodyAsText())
        }
    }

    suspend fun deleteUser(id: Long): Boolean =
        mainClient.delete("${ROUTE}/deleteUser") {
            setBody(mapOf("id" to id))
        }.status.isSuccess()

}