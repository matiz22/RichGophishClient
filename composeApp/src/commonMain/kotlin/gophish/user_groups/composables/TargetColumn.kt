package gophish.user_groups.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.matiz22.richgophishclient.AppRes
import group.domain.model.Target

@Composable
fun TargetColumn(targets: List<Target>, unPick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                OutlinedButton(onClick = { unPick() }){
                    Text(text = AppRes.string.cancel)
                }
            }
            items(targets) { target ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.weight(0.2f), text = target.firstName)
                    Text(modifier = Modifier.weight(0.2f), text = target.lastName)
                    Text(modifier = Modifier.weight(0.35f), text = target.email)
                    Text(modifier = Modifier.weight(0.25f), text = target.position)
                }
            }
        }
    }

}