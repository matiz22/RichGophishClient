package home.di

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() = startKoin {
    modules(
        listOf(commonModule())
    )
}