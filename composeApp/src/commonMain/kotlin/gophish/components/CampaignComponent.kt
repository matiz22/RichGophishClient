package gophish.components

import campaigns.domain.model.Campaign
import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class CampaignComponent(
    componentContext: ComponentContext,
    private val campaign: Campaign
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope =
}