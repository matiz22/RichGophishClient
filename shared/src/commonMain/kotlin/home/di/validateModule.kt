package home.di

import home.domain.use_case.ValidateEmail
import home.domain.use_case.ValidatePassword
import home.domain.use_case.ValidateUrl
import org.koin.dsl.module

fun validateModule() = module {
    single { ValidateEmail() }
    single { ValidatePassword() }
    single { ValidateUrl() }
}