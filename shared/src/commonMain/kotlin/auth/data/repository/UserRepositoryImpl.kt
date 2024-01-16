package auth.data.repository

import auth.data.remote.UserApi
import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import auth.domain.model.User
import auth.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun getUserById(idCredentials: IdCredentials): User {
        return userApi.fetchUserById(idCredentials)
    }

    override suspend fun getUserByEmail(emailCredentials: EmailCredentials): User {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(emailCredentials: EmailCredentials): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}