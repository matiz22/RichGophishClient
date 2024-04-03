package gophish.components

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class EmailTemplatesComponent(
    componentContext: ComponentContext
) : KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }

}