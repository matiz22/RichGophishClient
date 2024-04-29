package gophish.smtp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matiz22.richgophishclient.AppRes
import helper.provideReadableDateTime
import smtp.domain.model.Smtp


@Composable
fun SmtpCard(
    modifier: Modifier = Modifier,
    smtp: Smtp,
    onDelete: () -> Unit,
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AppRes.string.picked_smtp_name.format(smtp.name), fontSize = 16.sp
                )
                IconButton(
                    onClick = {
                        onDelete()
                    }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }
            }

            Text(
                text = AppRes.string.picked_smtp_from_address.format(smtp.fromAddress),
                fontSize = 16.sp
            )
            Text(
                text = AppRes.string.picked_smtp_ignore_cert_errors.format(smtp.ignoreCertErrors.toString()),
                fontSize = 16.sp
            )
            Text(
                text = AppRes.string.picked_smtp_interface_type.format(smtp.interfaceType),
                fontSize = 16.sp
            )
            Text(
                text = AppRes.string.picked_smtp_modified_date.format(provideReadableDateTime(smtp.modifiedDate)),
                fontSize = 16.sp
            )
            Text(
                text = AppRes.string.picked_smtp_username.format(smtp.username), fontSize = 16.sp
            )
            smtp.headers.forEach { header ->
                Text(modifier = Modifier, text = "${header.key}: ${header.value}")
            }
        }
    }
}