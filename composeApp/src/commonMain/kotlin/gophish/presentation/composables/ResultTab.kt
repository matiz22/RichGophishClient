package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import gophish.events.CampaignDetailsEvent
import gophish.presentation.state.PageState
import result.domain.model.Result
import kotlin.math.min

@Composable
fun ResultTab(
    modifier: Modifier = Modifier,
    results: List<Result>,
    onClick: (CampaignDetailsEvent) -> Unit,
    pageState: PageState,
    searchText: String
) {

    OutlinedCard(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, end = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onClick(CampaignDetailsEvent.PreviousPage) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                    Text(text = "${pageState.currentPage}/${pageState.maxPage}")
                    IconButton(onClick = { onClick(CampaignDetailsEvent.NextPage) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
                SearchField(
                    text = searchText,
                    onChange = onClick,
                    textHint = AppRes.string.search
                )
            }
            val startIndex = (pageState.currentPage - 1) * PageState.itemsPerPage
            results.subList(
                fromIndex = startIndex,
                toIndex = min(startIndex + PageState.itemsPerPage, results.size)
            ).forEach { result ->
                ResultItem(
                    result = result,
                    onClick = onClick
                )
            }
        }
    }
}