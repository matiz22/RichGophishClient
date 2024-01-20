package home.di

import auth.domain.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserComponent: KoinComponent {
    val userRepository by inject<UserRepository>()
}