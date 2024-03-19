package config.presentation.components

import com.arkivanov.decompose.ComponentContext
import configs.domain.model.GophishConfig
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

class HomeOfConfigComponent(
    componentContext: ComponentContext,
    config: GophishConfig
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { createScope(this) }


}