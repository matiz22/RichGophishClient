package gophish.di

import campaigns.data.remote.GophishHttpRequester
import campaigns.data.repository.CampaignRepositoryImpl
import campaigns.domain.repository.CampaignRepository
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import page.data.repository.PageRepositoryImpl
import page.domain.repository.PagesRepository
import template.data.repository.TemplatesRepositoryImpl
import template.domain.repository.TemplateRepository

fun gophishModule() = module {
    scope(named("gophishScope")) {
        scoped<GophishHttpRequester> { params ->
            GophishHttpRequester(
                host = params[0],
                apiKey = params[1]
            )
        }
        scoped<CampaignRepository> { params ->
            CampaignRepositoryImpl(get(parameters = { parametersOf(params[0], params[1]) }))
        }
        scoped<TemplateRepository> { params ->
            TemplatesRepositoryImpl(get())
        }
        scoped<PagesRepository> {
            PageRepositoryImpl(get())
        }
    }
}