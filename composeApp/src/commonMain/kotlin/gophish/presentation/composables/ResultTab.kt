package gophish.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gophish.events.CampaignDetailsEvent
import gophish.presentation.state.PageState
import result.domain.model.Result
import kotlin.math.min

@Composable
fun ResultTab(
    modifier: Modifier = Modifier,
    results: List<Result>,
    onClick: (CampaignDetailsEvent) -> Unit,
    pageState: PageState
) {

    OutlinedCard(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                IconButton(onClick = { onClick(CampaignDetailsEvent.PreviousPage) }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
                Text(text = pageState.currentPage.toString())
                IconButton(onClick = { onClick(CampaignDetailsEvent.NextPage) }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            }
            val startIndex = (pageState.currentPage - 1) * PageState.itemsPerPage
            results.subList(
                fromIndex = startIndex,
                toIndex = min(startIndex + PageState.itemsPerPage, results.size)
            ).forEach { result ->
                ResultItem(result = result)
            }
        }
    }
}