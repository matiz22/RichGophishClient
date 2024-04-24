package config.presentation.events

import config.presentation.states.IconButtonState

sealed class ScaffoldEvents {
    data class UpdateFloatingActionButton(val state: IconButtonState?) : ScaffoldEvents()
    data class ShowSnackBar(val message: String) : ScaffoldEvents()
    data class UpdateLeadingIconButton(val state: IconButtonState?): ScaffoldEvents()
}