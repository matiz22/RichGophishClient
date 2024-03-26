package gophish.di

import campaigns.data.remote.GophishHttpRequester
import campaigns.data.repository.CampaignRepositoryImpl
import campaigns.domain.repository.CampaignRepository
import config.presentation.components.HomeOfConfigComponent
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun campaignModule() = module {
    scope(named("campaignScope")) {
        scoped<GophishHttpRequester> { params ->
            GophishHttpRequester(
                host = params[0],
                apiKey = params[1]
            )
        }
        scoped<CampaignRepository> { params ->
            CampaignRepositoryImpl(get(parameters = { parametersOf(params[0], params[1]) }))
        }
    }
}