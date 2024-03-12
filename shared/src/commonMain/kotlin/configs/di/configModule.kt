package configs.di

import configs.data.remote.ConfigApi
import configs.data.repository.ConfigRepositoryImpl
import configs.domain.repository.ConfigRepository
import org.koin.dsl.module

fun configModule() = module {
    single<ConfigApi> { ConfigApi(get()) }
    single<ConfigRepository> { ConfigRepositoryImpl(get()) }
}