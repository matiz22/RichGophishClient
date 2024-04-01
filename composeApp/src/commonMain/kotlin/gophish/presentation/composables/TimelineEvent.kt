package gophish.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timeline.domain.model.Timeline

@Composable
fun TimelineEvent(timeLine: Timeline) {
    Text(text = timeLine.details.toString())
    println(timeLine.details)
}