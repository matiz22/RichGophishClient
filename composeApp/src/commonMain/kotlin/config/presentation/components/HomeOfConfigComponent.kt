package config.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignStats
import campaigns.domain.model.DataOrError
import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import config.presentation.events.HomeOfConfigEvent
import configs.domain.model.GophishConfig
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.inject
import org.koin.core.parameter.parameterSetOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class HomeOfConfigComponent(
    componentContext: ComponentContext,
    config: GophishConfig
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy {
        getKoin().createScope("gophishComponents", named("gophishScope"))
    }
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val campaignRepository: CampaignRepository by inject {
        parameterSetOf(config.url, config.apiKey)
    }
    private val _campaigns = MutableStateFlow<DataOrError<List<Campaign>>>(DataOrError())
    val campaigns: StateFlow<DataOrError<List<Campaign>>> = _campaigns.asStateFlow()

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    private val _pickedCampaignChannel = Channel<Campaign>()
    val pickedCampaignChannel = _pickedCampaignChannel.receiveAsFlow()

    var stats by mutableStateOf<CampaignStats?>(null)

    var pickingCampaign by mutableStateOf<Boolean>(false)

    init {
        lifecycle.doOnDestroy {
            scope.close()
            coroutineScope.cancel()
        }
    }

    fun onEvent(homeOfConfigEvent: HomeOfConfigEvent) {
        when (homeOfConfigEvent) {
            is HomeOfConfigEvent.DeleteCampaign -> {
                coroutineScope.launch {
                    val isCorrect = deleteCampaign(homeOfConfigEvent.id).await()
                    if (isCorrect.successful) {
                        updateCampaigns()
                    }
                }
            }

            is HomeOfConfigEvent.HideCampaigns -> {
                pickingCampaign = false
            }

            is HomeOfConfigEvent.PickCampaign -> {
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        _pickedCampaignChannel.send(homeOfConfigEvent.campaign)
                    }
                }

            }

            is HomeOfConfigEvent.ShowCampaigns -> {
                pickingCampaign = true
            }
        }
    }

    fun updateCampaigns() {
        coroutineScope.launch {
            val result = campaignRepository.getCampaigns()
            if (result.data != null) {
                _campaigns.emit(result)
            } else {
                _apiCallResult.send(ApiCallResult(successful = false, errorMessage = result.error))
                return@launch
            }
            stats = null
            campaigns.value.data?.forEach { campaign ->
                val summary = campaignRepository.getCampaignSummary(campaign.id)
                if (summary.data != null) {
                    stats = if (stats == null) {
                        summary.data!!.stats
                    } else {
                        stats?.copy(
                            total = stats?.total!! + summary.data!!.stats.total,
                            sent = stats?.sent!! + summary.data!!.stats.sent,
                            opened = stats?.opened!! + summary.data!!.stats.opened,
                            clicked = stats?.clicked!! + summary.data!!.stats.clicked,
                            submittedData = stats?.submittedData!! + summary.data!!.stats.submittedData,
                            emailReported = stats?.emailReported!! + summary.data!!.stats.emailReported,
                            error = stats?.error!! + summary.data!!.stats.error
                        )
                    }
                }
            }
        }
    }

    private fun deleteCampaign(id: Long): Deferred<ApiCallResult> {
        return coroutineScope.async {
            val result = campaignRepository.deleteCampaign(id)
            withContext(Dispatchers.Main) {
                runBlocking {
                    _apiCallResult.send(result)
                }
            }
            result
        }
    }
}