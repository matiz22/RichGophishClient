package configs.di

import configs.domain.use_case.ValidateApiKey
import configs.domain.use_case.ValidateName
import org.koin.dsl.module


fun validateConfigModule() = module {
    single { ValidateName() }
    single { ValidateApiKey() }
}