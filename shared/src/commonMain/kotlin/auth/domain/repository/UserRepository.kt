package auth.domain.repository

import auth.domain.model.EmailCredentials
import auth.domain.model.IdCredentials
import auth.domain.model.User

interface UserRepository {
    suspend fun getUserById(idCredentials: IdCredentials): User
    suspend fun getUserByEmail(emailCredentials: EmailCredentials): User
    suspend fun createUser(emailCredentials: EmailCredentials): User
    suspend fun deleteUser(id: Long): Boolean

}