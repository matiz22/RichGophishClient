package configs.domain.repository

import configs.domain.model.ConfigsOrError
import configs.domain.model.CreateGophishConfig
import configs.domain.model.EditGophishConfig
import configs.domain.model.GophishConfig
import home.domain.model.ApiCallResult

interface ConfigRepository {
    suspend fun createConfig(
        createGophishConfig: CreateGophishConfig
    ): ApiCallResult

    suspend fun editConfig(
        editGophishConfig: EditGophishConfig
    ): ApiCallResult

    suspend fun getConfigsForUser(
        userId: Long
    ): ConfigsOrError

    suspend fun deleteConfig(
        id: Long
    ): ApiCallResult
}