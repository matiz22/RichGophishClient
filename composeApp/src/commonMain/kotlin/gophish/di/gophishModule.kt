package gophish.di

import campaigns.data.repository.CampaignRepositoryImpl
import campaigns.domain.repository.CampaignRepository
import config.presentation.components.HomeOfConfigComponent
import org.koin.dsl.module

fun campaignModule() = module {
    scope<HomeOfConfigComponent> {
        scoped<CampaignRepository> { params ->
            CampaignRepositoryImpl(host = params.get(), apiKey = params.get())
        }
    }
}