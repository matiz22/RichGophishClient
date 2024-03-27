package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import result.domain.model.Result

@Composable
fun ResultItem(
    modifier: Modifier = Modifier,
    result: Result
) {
    OutlinedCard(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Box(modifier = Modifier.weight(1f)){
                Text(text = result.firstName)
            }
            Box(modifier = Modifier.weight(1f)){
                Text(text = result.lastName)
            }
            Box(modifier = Modifier.weight(1f)){
                Text(text = result.email)
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ){
                Text(text = result.status)
            }
        }
    }
}