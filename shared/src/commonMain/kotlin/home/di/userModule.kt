package home.di

import auth.data.remote.UserApi
import auth.data.repository.UserRepositoryImpl
import auth.domain.repository.UserRepository
import home.data.remote.mainClient
import org.koin.dsl.module

fun userModule() = module {
    single { mainClient }
    single<UserApi> { UserApi(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}