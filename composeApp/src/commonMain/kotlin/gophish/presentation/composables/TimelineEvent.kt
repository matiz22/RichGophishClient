package gophish.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import timeline.domain.model.Timeline

@Composable
fun TimelineEvent(
    modifier: Modifier = Modifier,
    timeline: Timeline
) {
    Column(
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
        val eventDetails = timeline.provideDetailsList()
        if (eventDetails?.payload?.email != null) {
            var isVisible by remember { mutableStateOf(false) }
            Row(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material3.Text(text = AppRes.string.show_passed_credential)
                IconButton(onClick = { isVisible = !isVisible }) {
                    if (isVisible) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = isVisible
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    eventDetails.payload.email?.forEach {
                        Text(
                            text = AppRes.string.passed_email.format(it),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    eventDetails.payload.password?.forEach {
                        Text(
                            text = AppRes.string.passed_password.format(it),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}