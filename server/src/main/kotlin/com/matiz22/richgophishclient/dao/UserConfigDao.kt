package com.matiz22.richgophishclient.dao

import configs.domain.model.CreateGophishConfig
import configs.domain.model.EditGophishConfig
import configs.domain.model.GophishConfig

interface UserConfigDao {
    suspend fun createConfig(
        createGophishConfig: CreateGophishConfig
    ): GophishConfig?

    suspend fun editConfig(
        editGophishConfig: EditGophishConfig
    ): Boolean

    suspend fun getConfigsForUser(
        userId: Long
    ): List<GophishConfig>

    suspend fun deleteConfig(
        id: Long
    ): Boolean
}