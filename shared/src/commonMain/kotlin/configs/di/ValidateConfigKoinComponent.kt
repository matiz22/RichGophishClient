package configs.di

import configs.domain.use_case.ValidateApiKey
import configs.domain.use_case.ValidateName
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ValidateConfigKoinComponent : KoinComponent {
    val validateName by inject<ValidateName>()
    val validateApiKey by inject<ValidateApiKey>()
}