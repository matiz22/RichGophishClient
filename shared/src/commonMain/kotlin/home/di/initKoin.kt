package home.di

import org.koin.core.context.startKoin


fun initKoin() = startKoin {
    modules(
        listOf(
            userModule(),
            validateModule()
        )
    )
}