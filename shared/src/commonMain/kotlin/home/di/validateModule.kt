package home.di

import home.domain.use_case.ValidateEmail
import home.domain.use_case.ValidatePassword
import org.koin.dsl.module

fun validateModule() = module {
    single { ValidateEmail() }
    single { ValidatePassword() }
}