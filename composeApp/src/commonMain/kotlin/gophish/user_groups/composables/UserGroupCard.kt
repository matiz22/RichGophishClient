package gophish.user_groups.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import group.domain.model.Group
import helper.provideReadableDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserGroupCard(
    modifier: Modifier = Modifier,
    group: Group,
    onClick: () -> Unit,
    delete: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = {
            onClick()
        }
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
                    text = AppRes.string.template_name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Row {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = {
                        delete()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = AppRes.string.template_modified_date)
                Text(text = provideReadableDateTime(group.modifiedDate))
            }
        }
    }
}