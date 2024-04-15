package root.di

import configs.di.configModule
import configs.di.validateConfigModule
import gophish.di.gophishModule
import home.di.userModule
import home.di.validateModule
import ollama.di.ollamaModule
import org.koin.core.context.startKoin


fun initKoin() = startKoin {
    modules(
        listOf(
            userModule(),
            validateModule(),
            configModule(),
            validateConfigModule(),
            gophishModule(),
            ollamaModule()
        )
    )
}