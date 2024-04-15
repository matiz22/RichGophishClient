package ollama.di

import ollama.data.repository.OllamaRepositoryImpl
import ollama.domain.repository.OllamaRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun ollamaModule() = module {
    scope(named("ollamaScope")) {
        scoped<OllamaRepository> {
            OllamaRepositoryImpl()
        }
    }
}