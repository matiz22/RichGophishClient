package config.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material.icons.sharp.Warning
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.matiz22.richgophishclient.AppRes
import config.presentation.events.ListOfConfigsEvent
import configs.domain.model.GophishConfig

@Composable
fun ConfigItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    gophishConfig: GophishConfig,
    onMenuClick: (ListOfConfigsEvent) -> Unit,
    onClick: (GophishConfig) -> Unit
) {
    var isHiddenApiKey by remember { mutableStateOf(true) }
    var showMenu by remember { mutableStateOf(false) }
    OutlinedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.clickable {
                onClick(gophishConfig)
            }.padding(16.dp),
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
                Box {
                    IconButton(onClick = {
                        showMenu = !showMenu
                    }) {
                        Icon(
                            imageVector = Icons.Sharp.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            onClick = {
                                onMenuClick(ListOfConfigsEvent.DeleteConfig(gophishConfig.id))
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Delete, contentDescription = null)
                            },
                            text = { Text(text = AppRes.string.delete) })
                        DropdownMenuItem(
                            onClick = {
                                onMenuClick(ListOfConfigsEvent.HandleDialog(gophishConfig))
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Edit, contentDescription = null)
                            },
                            text = { Text(text = AppRes.string.edit) })
                    }
                }
            }
            BasicTextField(
                value = gophishConfig.apiKey,
                onValueChange = {},
                enabled = false,
                textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
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
