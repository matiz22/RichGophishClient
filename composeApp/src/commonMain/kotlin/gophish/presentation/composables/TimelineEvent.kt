package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import timeline.domain.model.Timeline

@Composable
fun TimelineEvent(
    modifier: Modifier = Modifier,
    timeline: Timeline
) {
    Row(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimelineEventIcon(
                modifier = Modifier.size(50.dp),
                timeline = timeline
            )
            Text(
                text = timeline.message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}