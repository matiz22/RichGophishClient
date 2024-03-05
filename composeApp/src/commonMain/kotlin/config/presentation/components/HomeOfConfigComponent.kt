package config.presentation.components

import com.arkivanov.decompose.ComponentContext
import configs.domain.model.GophishConfig

class HomeOfConfigComponent(
    componentContext: ComponentContext,
    config: GophishConfig
) : ComponentContext by componentContext {

}