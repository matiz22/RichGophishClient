package gophish.user_groups.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import gophish.user_groups.domain.CreateTargetForm

@Composable
fun TargetCard(modifier: Modifier = Modifier, target: CreateTargetForm, onDelete: () -> Unit) {
    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AppRes.string.target_first_name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = target.firstname,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AppRes.string.target_last_name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = target.lastName,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AppRes.string.target_position,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = target.position,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AppRes.string.target_email,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = target.email,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            OutlinedButton(
                onClick = { onDelete() }
            ) {
                Text(AppRes.string.delete)
            }
        }
    }
}