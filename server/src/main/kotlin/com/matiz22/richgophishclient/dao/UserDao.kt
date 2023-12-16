package com.matiz22.richgophishclient.dao

import auth.model.User

interface UserDao {
    suspend fun logUserByEmail(email: String, password: String): User?
    suspend fun logUserBySavedCredential(id: Long, password: String): User?

    suspend fun createUser(
        email: String,
        password: String
    ): User? //Return Id of created User

    suspend fun deleteUser(id: Long): Boolean

    suspend fun checkIfExist(email: String): Boolean
}