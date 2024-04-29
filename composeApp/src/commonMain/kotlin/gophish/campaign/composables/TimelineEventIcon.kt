package gophish.campaign.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import timeline.domain.model.Timeline
import ui.SummaryChartColor

@Composable
fun TimelineEventIcon(
    modifier: Modifier = Modifier,
    timeline: Timeline
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.wrapContentSize().clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            when (timeline.message) {
                "Email Sent" -> {
                    Icon(
                        modifier = Modifier.fillMaxSize()
                            .background(
                                color = SummaryChartColor.SentSummaryChartColor.color,
                                shape = CircleShape
                            ).padding(4.dp),
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                "Email Opened" -> {
                    Icon(
                        modifier = Modifier.fillMaxSize()
                            .background(
                                color = SummaryChartColor.OpenedSummaryChartColor.color,
                                shape = CircleShape
                            ).padding(4.dp),
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                "Clicked Link" -> {
                    Icon(
                        modifier = Modifier.fillMaxSize()
                            .background(
                                color = SummaryChartColor.ClickedLinkColorSummary.color,
                                shape = CircleShape
                            ).padding(4.dp),
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                "Submitted Data" -> {
                    Icon(
                        modifier = Modifier.fillMaxSize()
                            .background(
                                color = SummaryChartColor.SubmittedDataColorSummary.color,
                                shape = CircleShape
                            ).padding(4.dp),
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                "Email Reported" -> {
                    Icon(
                        modifier = Modifier.fillMaxSize().background(
                            color = SummaryChartColor.EmailReportedColorSummary.color,
                            shape = CircleShape
                        ).padding(4.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}