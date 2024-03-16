package home.di

import home.domain.use_case.ValidateEmail
import home.domain.use_case.ValidatePassword
import home.domain.use_case.ValidateUrl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ValidatorsComponent : KoinComponent {
    val emailValidator by inject<ValidateEmail>()
    val passwordValidator by inject<ValidatePassword>()
    val urlValidator by inject<ValidateUrl>()
}