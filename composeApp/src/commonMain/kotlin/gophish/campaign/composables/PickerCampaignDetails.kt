package gophish.campaign.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PickerCampaignDetails(
    modifier: Modifier = Modifier,
    hint: String = "",
    pickedOption: String,
    options: List<String>,
    pickOption: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedCard() {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = hint)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pickedOption, style = MaterialTheme.typography.displayMedium
                )
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    if (isExpanded) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null
                        )
                    }
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                FlowColumn {
                    options.forEach { option ->
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp).clickable {
                                isExpanded = false
                                pickOption(option)
                            }.background(shape = CircleShape, color = Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option, style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}