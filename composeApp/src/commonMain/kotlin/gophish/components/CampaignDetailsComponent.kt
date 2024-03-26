package gophish.components

import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.DataOrError
import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.scope.Scope

class CampaignDetailsComponent(
    componentContext: ComponentContext,
    private val selectedCampaign: Campaign
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("campaignComponent") }

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _campaign = MutableStateFlow<DataOrError<Campaign>>(DataOrError())
    val campaign: StateFlow<DataOrError<Campaign>> = _campaign.asStateFlow()

    private val _campaignSummary = MutableStateFlow<DataOrError<CampaignSummary>>(DataOrError())
    val campaignSummary: StateFlow<DataOrError<CampaignSummary>> = _campaignSummary.asStateFlow()

    private val campaignRepository by inject<CampaignRepository>()


    init {
        updateCampaign()
        updateCampaignStats()
    }

    private fun updateCampaign(){
        coroutineScope.launch {
            val result = campaignRepository.getCampaign(selectedCampaign.id)
            _campaign.emit(result)
        }
    }

    private fun updateCampaignStats(){
        coroutineScope.launch {
            val result = campaignRepository.getCampaignSummary(selectedCampaign.id)
            _campaignSummary.emit(result)
        }
    }

}