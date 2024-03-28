package gophish.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.DataOrError
import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import gophish.events.CampaignDetailsEvent
import gophish.presentation.state.PageState
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

    var pageState by mutableStateOf(PageState(maxPage = 0))

    fun onEvent(campaignDetailsEvent: CampaignDetailsEvent) {
        when (campaignDetailsEvent) {
            is CampaignDetailsEvent.NextPage -> {
                if (campaign.value.data != null) {
                    if (pageState.currentPage < pageState.maxPage) {
                        pageState = pageState.copy(currentPage = pageState.currentPage + 1)
                    }
                }
            }

            is CampaignDetailsEvent.PreviousPage -> {
                if (campaign.value.data != null) {
                    if (pageState.currentPage - 1 > 0) {
                        pageState = pageState.copy(currentPage = pageState.currentPage - 1)
                    }
                }
            }
        }
    }

    init {
        updateData()
    }

    private fun updateData() {
        updateCampaign()
        updateCampaignStats()
    }

    private fun updateCampaign() {
        coroutineScope.launch {
            val result = campaignRepository.getCampaign(selectedCampaign.id)
            if (result.data != null) {
                pageState =
                    pageState.copy(maxPage = PageState.calculatePages(result.data!!.results.size))
                _campaign.emit(
                    result.copy(
                        data = result.data?.copy(
                            results = result.data!!.results.sortedWith(
                                compareBy({ it.firstName }, { it.lastName })
                            )
                        )
                    )
                )
                return@launch
            }
            _campaign.emit(result.copy())

        }
    }

    private fun updateCampaignStats() {
        coroutineScope.launch {
            val result = campaignRepository.getCampaignSummary(selectedCampaign.id)
            _campaignSummary.emit(result)
        }
    }

}