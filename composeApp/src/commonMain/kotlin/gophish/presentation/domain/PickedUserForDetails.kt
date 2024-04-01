package gophish.presentation.domain

import result.domain.model.Result
import timeline.domain.model.Timeline

data class PickedUserForDetails(
    val result: Result,
    val timelines: List<Timeline>
)
