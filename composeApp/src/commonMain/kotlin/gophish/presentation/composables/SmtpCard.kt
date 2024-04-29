package gophish.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matiz22.richgophishclient.AppRes
import helper.provideReadableDateTime
import smtp.domain.model.Smtp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmtpCard(
    modifier: Modifier = Modifier,
    smtp: Smtp,
    onClick: () -> Unit,
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
                Text(
                    text = smtp.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = AppRes.string.template_modified_date)
                if (smtp.modifiedDate != null) {
                    Text(text = provideReadableDateTime(smtp.modifiedDate!!))
                }
            }
        }
    }
}