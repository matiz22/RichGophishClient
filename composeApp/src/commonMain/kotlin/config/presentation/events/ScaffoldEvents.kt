package config.presentation.events

import config.presentation.states.FloatingActionButtonState

sealed class ScaffoldEvents {
    data class UpdateFloatingActionButton(val state: FloatingActionButtonState?) : ScaffoldEvents()
    data class ShowSnackBar(val message: String) : ScaffoldEvents()
}