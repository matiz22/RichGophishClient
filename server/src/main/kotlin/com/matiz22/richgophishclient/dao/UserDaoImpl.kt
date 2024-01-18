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
import org.mindrot.jbcrypt.BCrypt

class UserDaoImpl : UserDao {

    private fun resultRowUser(row: ResultRow) = User(
        id = row[UserTable.id],
        email = row[UserTable.email],
        password = row[UserTable.password]
    )

    override suspend fun logUserByEmail(email: String, password: String): User? = dbQuery {
        val userRow = UserTable.select {
            UserTable.email.eq(email)
        }.singleOrNull() ?: return@dbQuery null // User with the given email doesn't exist

        val storedHashedPassword =
            userRow[UserTable.password] // Assuming the column name is 'password'

        // Compare the provided password with the stored hashed password using BCrypt's checkpw function
        if (BCrypt.checkpw(password, storedHashedPassword)) {
            return@dbQuery resultRowUser(userRow)
        } else {
            return@dbQuery null
        }
    }

    override suspend fun logUserBySavedCredential(id: Long, password: String): User? =
        dbQuery {
            val userRow = UserTable.select {
                UserTable.id.eq(id)
            }.singleOrNull() ?: return@dbQuery null

            val storedHashedPassword = userRow[UserTable.password]

            if (BCrypt.checkpw(password, storedHashedPassword)) {
                return@dbQuery resultRowUser(userRow)
            } else {
                return@dbQuery null
            }
        }

    override suspend fun createUser(email: String, password: String): User? = dbQuery {
        val toBeInserted = UserTable.insert {
            it[UserTable.email] = email
            it[UserTable.password] = BCrypt.hashpw(password, BCrypt.gensalt())
        }
        toBeInserted.resultedValues?.singleOrNull()?.let(::resultRowUser)
    }

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

    override suspend fun checkIfExist(email: String): Boolean = dbQuery {
        val user = UserTable.select {
            UserTable.email.eq(email)
        }.map(::resultRowUser).singleOrNull()

        user != null
    }
}