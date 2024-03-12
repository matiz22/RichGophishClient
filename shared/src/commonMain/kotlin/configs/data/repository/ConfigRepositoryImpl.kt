package configs.data.repository

import configs.data.remote.ConfigApi
import configs.domain.model.ConfigsOrError
import configs.domain.model.CreateGophishConfig
import configs.domain.model.EditGophishConfig
import configs.domain.repository.ConfigRepository
import home.domain.model.ApiCallResult

class ConfigRepositoryImpl(private val configApi: ConfigApi) : ConfigRepository {
    override suspend fun createConfig(createGophishConfig: CreateGophishConfig): ApiCallResult {
        return configApi.createConfig(createGophishConfig)
    }

    override suspend fun editConfig(editGophishConfig: EditGophishConfig): ApiCallResult {
        return configApi.editConfig(editGophishConfig)
    }

    override suspend fun getConfigsForUser(userId: Long): ConfigsOrError {
        return configApi.fetchConfigs(userId)
    }

    override suspend fun deleteConfig(id: Long): ApiCallResult {
        return configApi.deleteConfig(id)
    }

}