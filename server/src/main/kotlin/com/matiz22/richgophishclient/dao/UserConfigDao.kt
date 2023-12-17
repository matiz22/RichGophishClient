package com.matiz22.richgophishclient.dao

import auth.model.config_gophish.CreateGophishConfig
import auth.model.config_gophish.EditGophishConfig
import auth.model.config_gophish.GophishConfig

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