package com.matiz22.richgophishclient.dao

import configs.domain.model.CreateGophishConfig
import configs.domain.model.EditGophishConfig
import configs.domain.model.GophishConfig
import com.matiz22.richgophishclient.model.DatabaseSingleton.dbQuery
import com.matiz22.richgophishclient.model.UserConfigTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class UserConfigDaoImpl : UserConfigDao {

    private fun resultRowConfig(row: ResultRow) = GophishConfig(
        id = row[UserConfigTable.id],
        userId = row[UserConfigTable.userId],
        url = row[UserConfigTable.url],
        name = row[UserConfigTable.name],
        apiKey = row[UserConfigTable.apiKey]
    )

    override suspend fun createConfig(createGophishConfig: CreateGophishConfig): GophishConfig? =
        dbQuery {
            val toBeInserted = UserConfigTable.insert {
                it[UserConfigTable.userId] = createGophishConfig.userId
                it[UserConfigTable.name] =createGophishConfig.name
                it[UserConfigTable.url] = createGophishConfig.url
                it[UserConfigTable.apiKey] = createGophishConfig.apiKey
            }
            toBeInserted.resultedValues?.singleOrNull()?.let(::resultRowConfig)
        }

    override suspend fun editConfig(editGophishConfig: EditGophishConfig): Boolean = dbQuery {
        UserConfigTable.update({ UserConfigTable.id eq editGophishConfig.id }) {
            if (editGophishConfig.apiKey != null) {
                it[apiKey] = editGophishConfig.apiKey!!
            }
            if (editGophishConfig.url != null) {
                it[url] = editGophishConfig.url!!
            }
            if (editGophishConfig.name != null) {
                it[name] = editGophishConfig.name!!
            }
        } == 1
    }

    override suspend fun getConfigsForUser(userId: Long): List<GophishConfig> = dbQuery {
        UserConfigTable.select { UserConfigTable.userId eq userId }.map(::resultRowConfig)
    }

    override suspend fun deleteConfig(id: Long): Boolean = dbQuery {
        UserConfigTable.deleteWhere { UserConfigTable.id eq id } > 0
    }
}