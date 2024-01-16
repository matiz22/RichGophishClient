package home.di

import auth.data.remote.UserApi
import auth.data.repository.UserRepositoryImpl
import auth.domain.repository.UserRepository
import home.data.mainClient
import org.koin.dsl.module

fun commonModule() = module {
    single { mainClient }
    single<UserApi> { UserApi(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}