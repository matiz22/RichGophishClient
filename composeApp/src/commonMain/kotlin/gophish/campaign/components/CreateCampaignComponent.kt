package gophish.campaign.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.domain.repository.CampaignRepository
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.campaign.events.CreateCampaignEvent
import gophish.campaign.state.CampaignFormState
import group.domain.repository.UserGroupRepository
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import page.domain.repository.PagesRepository
import smtp.domain.repository.SmtpRepository
import template.domain.repository.TemplateRepository

class CreateCampaignComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext, KoinScopeComponent {

    override val scope: Scope by lazy {
        getKoin().getOrCreateScope("gophishComponents", named("gophishScope"))
    }
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        getAvailableResources()
        lifecycle.doOnDestroy { coroutineScope.cancel() }
    }

    var campaignFormState by mutableStateOf(CampaignFormState())


    private val campaignRepository: CampaignRepository by inject<CampaignRepository>()
    private val templateRepository: TemplateRepository by inject<TemplateRepository>()
    private val groupRepository: UserGroupRepository by inject<UserGroupRepository>()
    private val pageRepository: PagesRepository by inject<PagesRepository>()
    private val smtpRepository: SmtpRepository by inject<SmtpRepository>()

    private val _pagesNames = MutableStateFlow<List<String>>(emptyList())
    val pagesNames = _pagesNames.asStateFlow()

    private val _templatesNames = MutableStateFlow<List<String>>(emptyList())
    val templatesNames = _templatesNames.asStateFlow()

    private val _groupsNames = MutableStateFlow<List<String>>(emptyList())
    val groupsNames = _groupsNames.asStateFlow()

    private val _smtpsNames = MutableStateFlow<List<String>>(emptyList())
    val smtpsNames = _smtpsNames.asStateFlow()

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    fun getAvailableResources() {
        val result = coroutineScope.launch {
            templateRepository.getTemplates().data?.forEach { template ->
                _templatesNames.value += template.name
            }
            groupRepository.getGroups().data?.forEach { group ->
                _groupsNames.value += group.name
            }
            pageRepository.getPages().data?.forEach { page ->
                _pagesNames.value += page.name
            }
            smtpRepository.getSmtpProfiles().data?.forEach { smtp ->
                _smtpsNames.value += smtp.name
            }
        }
    }

    fun onEvent(createCampaignEvent: CreateCampaignEvent) {
        when (createCampaignEvent) {
            is CreateCampaignEvent.AddGroup -> {
                campaignFormState =
                    campaignFormState.copy(pickedGroups = campaignFormState.pickedGroups + createCampaignEvent.name)
            }

            is CreateCampaignEvent.DeleteGroup -> {
                campaignFormState =
                    campaignFormState.copy(pickedGroups = campaignFormState.pickedGroups - createCampaignEvent.name)
            }

            is CreateCampaignEvent.Submit -> createCampaign()

            is CreateCampaignEvent.UpdateName -> {
                campaignFormState = campaignFormState.copy(name = createCampaignEvent.name)
            }

            is CreateCampaignEvent.UpdatePage -> {
                campaignFormState = campaignFormState.copy(page = createCampaignEvent.name)
            }

            is CreateCampaignEvent.UpdateTemplate -> {
                campaignFormState = campaignFormState.copy(template = createCampaignEvent.name)
            }

            is CreateCampaignEvent.UpdateUrl -> {
                campaignFormState = campaignFormState.copy(url = createCampaignEvent.name)
            }

            is CreateCampaignEvent.UpdateSmtp -> {
                campaignFormState = campaignFormState.copy(smtp = createCampaignEvent.name)
            }
        }
    }

    private fun createCampaign() {
        coroutineScope.launch {
            val result = campaignRepository.createCampaign(
                createCampaign = campaignFormState.toCreateCampaignRequest()
            )
            withContext(Dispatchers.Main) {
                _apiCallResult.send(result)
            }
        }
    }
}
