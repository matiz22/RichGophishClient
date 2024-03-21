package config.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignStats
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.DataOrError
import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import configs.domain.model.GophishConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _campaigns = MutableStateFlow<DataOrError<List<Campaign>>>(DataOrError())
    val campaigns: StateFlow<DataOrError<List<Campaign>>> = _campaigns.asStateFlow()

    var stats by mutableStateOf(CampaignStats())

    init {
        coroutineScope.launch {
            updateCampaigns()
        }
    }

    private fun updateCampaigns() {
        coroutineScope.launch {
            stats = CampaignStats()
            _campaigns.emit(campaignRepository.getCampaigns())
            campaigns.value.data?.forEach { campaign ->
                val summary = campaignRepository.getCampaignSummary(campaign.id)
                if (summary.data != null) {
                    stats = stats.copy(
                        total = stats.total + summary.data!!.stats.total,
                        sent = stats.sent + summary.data!!.stats.sent,
                        opened = stats.opened + summary.data!!.stats.opened,
                        clicked = stats.clicked + summary.data!!.stats.clicked,
                        submittedData = stats.submittedData + summary.data!!.stats.submittedData,
                        emailReported = stats.emailReported + summary.data!!.stats.emailReported,
                        error = stats.error + summary.data!!.stats.error
                    )
                }
            }
        }
    }
}