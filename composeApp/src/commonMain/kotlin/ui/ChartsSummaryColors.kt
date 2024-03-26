package ui

import androidx.compose.ui.graphics.Color

sealed class SummaryChartColor(val color: Color) {
    data object SentSummaryChartColor : SummaryChartColor(Color(0xFF1abc9c))
    data object OpenedSummaryChartColor : SummaryChartColor(Color(0xFFf9bf3b))
    data object ClickedLinkColorSummary : SummaryChartColor(Color(0xFFF39C12))
    data object SubmittedDataColorSummary : SummaryChartColor(Color(0xFFf05b4f))
    data object EmailReportedColorSummary : SummaryChartColor(Color(0xFF45d6ef))
}
