package config.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material.icons.sharp.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import configs.domain.model.GophishConfig

@Composable
fun ConfigItem(modifier: Modifier = Modifier.fillMaxWidth(), gophishConfig: GophishConfig) {
    var isHiddenApiKey by remember { mutableStateOf(true) }
    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = gophishConfig.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = gophishConfig.url)
                }
                IconButton(onClick = {
                    isHiddenApiKey = !isHiddenApiKey
                }) {
                    Icon(
                        imageVector = Icons.Sharp.MoreVert,
                        contentDescription = null
                    )
                }
            }
            BasicTextField(
                value = gophishConfig.apiKey,
                onValueChange = {},
                enabled = false,
                visualTransformation = if (isHiddenApiKey) PasswordVisualTransformation('*') else VisualTransformation.None,
                decorationBox = { textBox ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        textBox()
                        IconButton(onClick = {
                            isHiddenApiKey = !isHiddenApiKey
                        }) {
                            Icon(
                                imageVector = Icons.Sharp.Warning,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    }

}