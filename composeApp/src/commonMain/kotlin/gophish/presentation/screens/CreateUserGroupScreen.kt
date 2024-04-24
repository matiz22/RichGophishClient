package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.presentation.domain.CreateTargetForm
import gophish.presentation.domain.CreateUserGroupForm
import gophish.presentation.events.CreatePageEvent
import gophish.presentation.events.CreateUserGroupEvent

@Composable
fun CreateUserGroupScreen(
    modifier: Modifier = Modifier,
    form: CreateUserGroupForm,
    onEvent: (CreateUserGroupEvent) -> Unit,
    navigateBack: () -> Unit,
    createTargetForm: CreateTargetForm,
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = AppRes.string.capture_credentials)
                    Switch(
                        checked = form.captureCredentials,
                        onCheckedChange = {
                            onEvent(CreatePageEvent.UpdateCaptureCredential)
                        }
                    )
                    if (form.captureCredentials) {
                        Text(text = AppRes.string.capture_password)
                        Switch(
                            checked = form.capturePassword,
                            onCheckedChange = {
                                onEvent(CreatePageEvent.UpdateCapturePassword)
                            }
                        )
                    }

                }
                Row {
                    Button(onClick = {
                        onEvent(CreatePageEvent.GeneratePage)
                    }, enabled = form.responseNotBeingCreated) {
                        Text(AppRes.string.generate_email)
                    }
                    OutlinedButton(onClick = {
                        onPreview(form)
                    }) {
                        Text(AppRes.string.preview)
                    }
                }
            }
        }

        item {
            Column() {
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.name,
                    onValueChange = {
                        onEvent(CreatePageEvent.UpdateName(it))
                    },
                    hintText = AppRes.string.template_name,
                    errorMessage = form.nameError,
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.redirectUrl,
                    onValueChange = {
                        onEvent(CreatePageEvent.UpdateRedirectUrl(it))
                    },
                    hintText = AppRes.string.redirect_url,
                    errorMessage = form.redirectUrlError
                )

                TextField(
                    modifier = modifier.fillMaxWidth(),
                    value = form.html,
                    onValueChange = {
                        onEvent(CreatePageEvent.UpdateHtml(it))
                    },
                    placeholder = { Text(AppRes.string.html_mode) },
                    minLines = 20
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    onEvent(CreatePageEvent.AddPage)
                }) {
                    Text(text = AppRes.string.add_template)
                }
                Button(onClick = {
                    navigateBack()
                }) {
                    Text(text = AppRes.string.cancel)
                }
            }
        }
    }
}