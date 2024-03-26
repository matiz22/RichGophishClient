package config.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import config.presentation.domain.MenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemPosition(menuItem: MenuItem) {
    OutlinedCard(
        onClick = {
            menuItem.action()
        }
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = menuItem.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}