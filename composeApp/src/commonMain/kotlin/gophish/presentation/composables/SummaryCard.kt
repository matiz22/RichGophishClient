package gophish.presentation.composables


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import campaigns.domain.model.CampaignStats
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.matiz22.richgophishclient.AppRes
import ui.SummaryChartColor


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    stats: CampaignStats?
) {
    var isExpanded by remember { mutableStateOf(false) }
    OutlinedCard(
        modifier = modifier
    ) {
        if (title != null) {
            Text(
                modifier = modifier.fillMaxWidth().padding(16.dp),
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = AppRes.string.expand_all_configs)
            IconButton(onClick = { isExpanded = !isExpanded }) {
                if (isExpanded) {
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
            visible = isExpanded
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                if (stats != null) {
                    val total = PieChartData(
                        data = stats.total.toDouble(),
                        color = Color.Gray,
                        partName = AppRes.string.campaign_stats_total.format(stats.total.toString())
                    )

                    PieChart(
                        modifier = Modifier.size(width = 400.dp, height = 500.dp),
                        pieChartData = listOf(
                            total,
                            PieChartData(
                                data = stats.sent.toDouble(),
                                color = SummaryChartColor.SentSummaryChartColor.color,
                                partName = AppRes.string.campaign_stats_sent.format(stats.sent.toString())
                            )
                        )
                    )

                    PieChart(
                        modifier = Modifier.size(width = 400.dp, height = 500.dp),
                        pieChartData = listOf(
                            total,
                            PieChartData(
                                data = stats.opened.toDouble(),
                                color = SummaryChartColor.OpenedSummaryChartColor.color,
                                partName = AppRes.string.campaign_stats_opened.format(stats.opened.toString())
                            )
                        )
                    )

                    PieChart(
                        modifier = Modifier.size(width = 400.dp, height = 500.dp),
                        pieChartData = listOf(
                            total,
                            PieChartData(
                                data = stats.clicked.toDouble(),
                                color = SummaryChartColor.ClickedLinkColorSummary.color,
                                partName = AppRes.string.campaign_stats_clicked.format(stats.clicked.toString())
                            )
                        )
                    )

                    PieChart(
                        modifier = Modifier.size(width = 400.dp, height = 500.dp),
                        pieChartData = listOf(
                            total,
                            PieChartData(
                                data = stats.submittedData.toDouble(),
                                color = SummaryChartColor.SubmittedDataColorSummary.color,
                                partName = AppRes.string.campaign_stats_submitted_data.format(stats.submittedData.toString())
                            )
                        )
                    )

                    PieChart(
                        modifier = Modifier.size(width = 400.dp, height = 500.dp),
                        pieChartData = listOf(
                            total,
                            PieChartData(
                                data = stats.emailReported.toDouble(),
                                color = SummaryChartColor.EmailReportedColorSummary.color,
                                partName = AppRes.string.campaign_stats_email_reported.format(stats.emailReported.toString())
                            )
                        )
                    )

                    PieChart(
                        modifier = Modifier.size(width = 400.dp, height = 500.dp),
                        pieChartData = listOf(
                            total,
                            PieChartData(
                                data = stats.error.toDouble(),
                                color = Color.Red,
                                partName = AppRes.string.campaign_stats_error.format(stats.error.toString())
                            )
                        )
                    )
                }
            }
        }
    }
}
