package auth.data.repository

import auth.data.remote.UserApi
import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import auth.domain.model.UserOrError
import auth.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun getUserById(idCredentials: IdCredentials): UserOrError {
        return userApi.fetchUserById(idCredentials)
    }

    override suspend fun getUserByEmail(emailCredentials: EmailCredentials): UserOrError{
        return userApi.fetchUserByEmail(emailCredentials)
    }

    override suspend fun createUser(emailCredentials: EmailCredentials): UserOrError {
        return userApi.createUser(emailCredentials)
    }

    override suspend fun deleteUser(id: Long): Boolean {
        return userApi.deleteUser(id)
    }
}