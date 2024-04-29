package gophish.email_templates.screens

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
import gophish.email_templates.domain.CreateTemplateForm
import gophish.email_templates.events.CreateEmailTemplatesEvent

@Composable
fun CreateEmailTemplateScreen(
    modifier: Modifier = Modifier,
    form: CreateTemplateForm,
    onEvent: (CreateEmailTemplatesEvent) -> Unit,
    navigateBack: () -> Unit,
    onPreview: (CreateTemplateForm) -> Unit
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
                    Text(text = AppRes.string.text_mode)
                    Switch(
                        checked = form.isHTML,
                        onCheckedChange = {
                            onEvent(CreateEmailTemplatesEvent.ChangeFormMode)
                        }
                    )
                    Text(text = AppRes.string.html_mode)
                }
                Row {
                    if (form.isHTML) {
                        Button(onClick = {
                            onEvent(CreateEmailTemplatesEvent.AddOllamaEmail)
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
        }

        item {
            Column() {
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.name,
                    onValueChange = {
                        onEvent(CreateEmailTemplatesEvent.UpdateName(it))
                    },
                    hintText = AppRes.string.template_name,
                    errorMessage = form.nameError,
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.subject,
                    onValueChange = {
                        onEvent(CreateEmailTemplatesEvent.UpdateSubject(it))
                    },
                    hintText = AppRes.string.email_hint
                )
                if (form.isHTML) {
                    TextField(
                        modifier = modifier.fillMaxWidth(),
                        value = form.html,
                        onValueChange = {
                            onEvent(CreateEmailTemplatesEvent.UpdateHtml(it))
                        },
                        placeholder = { Text(AppRes.string.html_mode) },
                        minLines = 20
                    )
                } else {
                    TextField(
                        modifier = modifier.fillMaxWidth(),
                        value = form.text,
                        onValueChange = {
                            onEvent(CreateEmailTemplatesEvent.UpdateHtml(it))
                        },
                        placeholder = { Text(AppRes.string.text_hint) },
                        minLines = 20
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    onEvent(CreateEmailTemplatesEvent.AddTemplate)
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