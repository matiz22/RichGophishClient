package com.matiz22.richgophishclient.dao

import auth.domain.model.User
import com.matiz22.richgophishclient.model.DatabaseSingleton.dbQuery
import com.matiz22.richgophishclient.model.UserConfigTable
import com.matiz22.richgophishclient.model.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Implementation of the [UserDao] interface providing methods to interact with the user database.
 */
class UserDaoImpl : UserDao {

    /**
     * Maps a [ResultRow] to a [User] object.
     *
     * @param row ResultRow containing user data.
     * @return User object mapped from the ResultRow.
     */
    private fun resultRowUser(row: ResultRow) = User(
        id = row[UserTable.id],
        email = row[UserTable.email],
        password = row[UserTable.password]
    )

    /**
     * Logs in a user by email and password.
     *
     * @param email User's email.
     * @param password User's password.
     * @return User object if login is successful, null otherwise.
     */
    override suspend fun logUserByEmail(email: String, password: String): User? = dbQuery {
        val userRow = UserTable.select {
            UserTable.email.eq(email)
        }.singleOrNull() ?: return@dbQuery null

        val storedHashedPassword = userRow[UserTable.password]
        val hash = PBEKeySpec(
            password.toCharArray(),
            email.toByteArray(),
            65536,
            128
        )
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hashedPassword = String(factory.generateSecret(hash).encoded)

        if (storedHashedPassword == hashedPassword) {
            return@dbQuery resultRowUser(userRow)
        } else {
            return@dbQuery null
        }
    }

    /**
     * Logs in a user by saved credentials.
     *
     * @param id User's ID.
     * @param password User's password.
     * @return User object if login is successful, null otherwise.
     */
    override suspend fun logUserBySavedCredential(id: Long, password: String): User? =
        dbQuery {
            val userRow = UserTable.select {
                UserTable.id.eq(id)
            }.singleOrNull() ?: return@dbQuery null
            val storedHashedPassword = userRow[UserTable.password]
            val hash = PBEKeySpec(
                password.toCharArray(),
                userRow[UserTable.email].toByteArray(),
                65536,
                128
            )
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val hashedPassword = String(factory.generateSecret(hash).encoded)
            if (hashedPassword == storedHashedPassword) {
                return@dbQuery resultRowUser(userRow)
            } else {
                return@dbQuery null
            }
        }

    /**
     * Creates a new user.
     *
     * @param email User's email.
     * @param password User's password.
     * @return User object if creation is successful, null otherwise.
     */
    override suspend fun createUser(email: String, password: String): User? = dbQuery {
        val hash = PBEKeySpec(password.toCharArray(), email.toByteArray(), 65536, 128)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val toBeInserted = UserTable.insert {
            it[UserTable.email] = email
            it[UserTable.password] = String(factory.generateSecret(hash).encoded)
        }
        toBeInserted.resultedValues?.singleOrNull()?.let(::resultRowUser)
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User's ID.
     * @return True if deletion is successful, false otherwise.
     */
    override suspend fun deleteUser(id: Long?): Boolean {
        if (id == null) {
            return false
        }
        return dbQuery {
            val userConfigDeleteCount = UserConfigTable.deleteWhere { UserConfigTable.userId eq id }
            val userDeleteCount = UserTable.deleteWhere { UserTable.id eq id }
            userConfigDeleteCount + userDeleteCount > 0
        }
    }

    /**
     * Checks if a user exists by email.
     *
     * @param email User's email.
     * @return True if user exists, false otherwise.
     */
    override suspend fun checkIfExist(email: String): Boolean = dbQuery {
        val user = UserTable.select {
            UserTable.email.eq(email)
        }.map(::resultRowUser).singleOrNull()

        user != null
    }
}