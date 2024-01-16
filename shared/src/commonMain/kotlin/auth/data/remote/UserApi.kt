package auth.data.remote

import auth.domain.model.IdCredentials
import auth.domain.model.User
import home.data.mainClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody


class UserApi(mainClient: HttpClient) {
    suspend fun fetchUserById(idCredentials: IdCredentials): User {
        return mainClient.post("/user/userById") {
            setBody(idCredentials)
        }.body()
    }
}