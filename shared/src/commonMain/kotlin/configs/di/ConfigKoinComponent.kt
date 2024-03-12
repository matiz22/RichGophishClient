package configs.di

import configs.domain.repository.ConfigRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConfigKoinComponent : KoinComponent {
    val configRepository by inject<ConfigRepository> ()
}