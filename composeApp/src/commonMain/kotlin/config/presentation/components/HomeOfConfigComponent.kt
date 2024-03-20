package config.presentation.components

import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import configs.domain.model.GophishConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.inject
import org.koin.core.parameter.parameterSetOf
import org.koin.core.scope.Scope

class HomeOfConfigComponent(
    componentContext: ComponentContext,
    config: GophishConfig
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { createScope(this) }
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val campaignRepository: CampaignRepository by inject {
        parameterSetOf(
            config.url,
            config.apiKey
        )
    }

    init {
        coroutineScope.launch {
            val test = campaignRepository.getCampaigns()
            print(test.toString())
        }
    }
}