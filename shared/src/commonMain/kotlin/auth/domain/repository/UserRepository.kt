package auth.domain.repository

import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import auth.domain.model.User
import auth.domain.model.UserOrError

interface UserRepository {
    suspend fun getUserById(idCredentials: IdCredentials): UserOrError
    suspend fun getUserByEmail(emailCredentials: EmailCredentials): UserOrError
    suspend fun createUser(emailCredentials: EmailCredentials): UserOrError
    suspend fun deleteUser(id: Long): Boolean

}