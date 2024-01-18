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
    suspend fun fetchUserById(idCredentials: IdCredentials): UserOrError {
        val request = mainClient.post("/user/userById") {
            setBody(idCredentials)
        }
        return if (request.status.isSuccess()) {
            UserOrError(user = request.body())
        } else {
            UserOrError(error = request.bodyAsText())
        }
    }

    suspend fun fetchUserByEmail(emailCredentials: EmailCredentials): UserOrError {
        val request = mainClient.post("/user/userByEmail") {
            setBody(emailCredentials)
        }
        return if (request.status.isSuccess()) {
            UserOrError(user = request.body())
        } else {
            UserOrError(error = request.bodyAsText())
        }
    }

    suspend fun createUser(emailCredentials: EmailCredentials): UserOrError {
        val request = mainClient.post("/user/createUser") {
            setBody(emailCredentials)
        }
        return if (request.status.isSuccess()) {
            UserOrError(user = request.body())
        } else {
            UserOrError(error = request.bodyAsText())
        }
    }

    suspend fun deleteUser(id: Long): Boolean =
        mainClient.delete("/user/deleteUser") {
            setBody(mapOf("id" to id))
        }.status.isSuccess()

}