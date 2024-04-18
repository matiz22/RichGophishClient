package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.domain.model.CreateTemplateForm
import gophish.presentation.events.CreateEmailTemplatesEvent

@Composable
fun CreateEmailTemplateScreen(
    modifier: Modifier = Modifier,
    form: CreateTemplateForm,
    onEvent: (CreateEmailTemplatesEvent) -> Unit,
    navigateBack: () -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
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
            if (form.isHTML) {
                Button(onClick = {
                    onEvent(CreateEmailTemplatesEvent.AddOllamaEmail)
                }) {
                    Text(AppRes.string.generate_email)
                }
            }
        }

        Column() {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.name,
                onValueChange = {
                    onEvent(CreateEmailTemplatesEvent.UpdateName(it))
                },
                hintText = AppRes.string.template_name,
                errorMessage = form.nameError,
                minLines = 20
            )
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.subject,
                onValueChange = {
                    onEvent(CreateEmailTemplatesEvent.UpdateSubject(it))
                },
                minLines = 20
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
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.text,
                    onValueChange = {
                        onEvent(CreateEmailTemplatesEvent.UpdateText(it))
                    },
                    minLines = 20
                )
            }
        }
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