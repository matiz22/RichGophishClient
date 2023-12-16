package com.matiz22.richgophishclient.dao

import auth.model.User
import com.matiz22.richgophishclient.model.DatabaseSingleton.dbQuery
import com.matiz22.richgophishclient.model.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.mindrot.jbcrypt.BCrypt
import java.lang.Exception

class UserDaoImpl : UserDao {

    private fun resultRowUser(row: ResultRow) = User(
        id = row[UserTable.id],
        email = row[UserTable.email],
        password = row[UserTable.password]
    )

    override suspend fun logUserByEmail(email: String, password: String): User? = dbQuery {
        UserTable.select {
            UserTable.email.eq(email) and UserTable.password.eq(
                BCrypt.hashpw(
                    password,
                    BCrypt.gensalt()
                )
            )
        }.map(::resultRowUser).singleOrNull()
    }

    override suspend fun logUserBySavedCredential(id: Long, password: String): User? =
        dbQuery {
            UserTable.select {
                UserTable.id.eq(id) and UserTable.password.eq(
                    BCrypt.hashpw(
                        password,
                        BCrypt.gensalt()
                    )
                )
            }.map(::resultRowUser).singleOrNull()
        }

    override suspend fun createUser(email: String, password: String): User? = dbQuery {
        val toBeInserted = UserTable.insert {
            it[UserTable.email] = email
            it[UserTable.password] = BCrypt.hashpw(password, BCrypt.gensalt())
        }
        toBeInserted.resultedValues?.singleOrNull()?.let(::resultRowUser)
    }

    override suspend fun deleteUser(id: Long): Boolean = dbQuery {
        UserTable.deleteWhere { UserTable.id eq id } > 0
    }

    override suspend fun checkIfExist(email: String): Boolean = dbQuery {
        val user = UserTable.select {
            UserTable.email.eq(email)
        }.map(::resultRowUser).singleOrNull()

        user != null
    }
}