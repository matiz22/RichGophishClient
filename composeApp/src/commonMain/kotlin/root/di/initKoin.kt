package root.di

import configs.di.configModule
import configs.di.validateConfigModule
import home.di.userModule
import home.di.validateModule
import org.koin.core.context.startKoin


fun initKoin() = startKoin {
    modules(
        listOf(
            userModule(),
            validateModule(),
            configModule(),
            validateConfigModule()
            campaignModule()
        )
    )
}