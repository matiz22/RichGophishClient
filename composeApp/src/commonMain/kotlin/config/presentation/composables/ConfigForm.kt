package config.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import config.presentation.events.ConfigDialogMode
import config.presentation.events.ListOfConfigsEvent
import config.presentation.states.ConfigFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigForm(configFormState: ConfigFormState, onEvent: (ListOfConfigsEvent) -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onEvent(ListOfConfigsEvent.HandleDialog())
        },
        content = {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        text = configFormState.name,
                        onValueChange = {
                            onEvent(ListOfConfigsEvent.OnNameUpdate(it))
                        },
                        hintText = AppRes.string.name_hint,
                        errorMessage = configFormState.nameError
                    )
                    InputField(
                        text = configFormState.url,
                        onValueChange = {
                            onEvent(ListOfConfigsEvent.OnUrlUpdate(it))
                        },
                        hintText = AppRes.string.url_hint,
                        errorMessage = configFormState.urlError
                    )
                    InputField(
                        text = configFormState.apiKey,
                        onValueChange = {
                            onEvent(ListOfConfigsEvent.OnApiKeyUpdate(it))
                        },
                        hintText = AppRes.string.api_key_hint,
                        errorMessage = configFormState.apiKeyError
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (configFormState.mode == ConfigDialogMode.EDIT) {
                            Button(onClick = {
                                onEvent(ListOfConfigsEvent.EditConfig)
                            }) {
                                Text(text = AppRes.string.edit_config)
                            }
                        } else {
                            Button(onClick = {
                                onEvent(ListOfConfigsEvent.AddConfig)
                            }) {
                                Text(text = AppRes.string.add_config)
                            }
                        }
                        OutlinedButton(
                            onClick = {
                                onEvent(ListOfConfigsEvent.HandleDialog())
                            }
                        ) {
                            Text(text = AppRes.string.cancel)
                        }
                    }
                }
            }
        }
    )
}