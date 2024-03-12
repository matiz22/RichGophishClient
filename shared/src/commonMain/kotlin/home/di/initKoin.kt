package home.di

import configs.di.configModule
import org.koin.core.context.startKoin


fun initKoin() = startKoin {
    modules(
        listOf(
            userModule(),
            validateModule(),
            configModule()
        )
    )
}