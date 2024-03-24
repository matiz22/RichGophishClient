package gophish.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import campaigns.domain.model.CampaignStats
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData
import com.matiz22.richgophishclient.AppRes


@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    stats: CampaignStats?
) {
    val total = PieChartData(
        data = stats.total.toDouble(),
        color = Color.Gray,
        partName = AppRes.string.campaign_stats_total.format(stats.total.toString())
    )
    OutlinedCard(
        modifier = modifier
    ) {
        LazyVerticalGrid(
            modifier = Modifier.wrapContentSize(),
            columns = GridCells.FixedSize(400.dp),
            userScrollEnabled = false
        ) {
            item {
                PieChart(
                    modifier = Modifier.size(width = 400.dp, height = 500.dp),
                    pieChartData = listOf(
                        PieChartData(
                            data = stats.sent.toDouble(),
                            color = Color(26, 188, 156),
                            partName = AppRes.string.campaign_stats_sent.format(stats.sent.toString())
                        )
                    )
                )

            }
            item {
                PieChart(
                    modifier = Modifier.size(width = 400.dp, height = 500.dp),
                    pieChartData = listOf(
                        total,
                        PieChartData(
                            data = summary.opened.toDouble(),
                            color = Color(26, 188, 156),
                            partName = AppRes.string.campaign_stats_opened.format(summary.opened.toString())
                        )
                    )
                )

            }
            item {
                PieChart(
                    modifier = Modifier.size(width = 400.dp, height = 500.dp),
                    pieChartData = listOf(
                        total,
                        PieChartData(
                            data = summary.clicked.toDouble(),
                            color = Color(26, 188, 156),
                            partName = AppRes.string.campaign_stats_clicked.format(summary.clicked.toString())
                        )
                    )
                )
            }
            item {
                PieChart(
                    modifier = Modifier.size(width = 400.dp, height = 500.dp),
                    pieChartData = listOf(
                        total,
                        PieChartData(
                            data = summary.submittedData.toDouble(),
                            color = Color(26, 188, 156),
                            partName = AppRes.string.campaign_stats_submitted_data.format(summary.submittedData.toString())
                        )
                    )
                )
            }
            item {
                PieChart(
                    modifier = Modifier.size(width = 400.dp, height = 500.dp),
                    pieChartData = listOf(
                        total,
                        PieChartData(
                            data = summary.emailReported.toDouble(),
                            color = Color(26, 188, 156),
                            partName = AppRes.string.campaign_stats_email_reported.format(summary.emailReported.toString())
                        )
                    )
                )

            }
            item {
                PieChart(
                    modifier = Modifier.size(width = 400.dp, height = 500.dp),
                    pieChartData = listOf(
                        total,
                        PieChartData(
                            data = summary.error.toDouble(),
                            color = Color(26, 188, 156),
                            partName = AppRes.string.campaign_stats_error.format(summary.error.toString())
                        )
                    )
                )
            }
        }
    }
}