package gophish.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.domain.model.CreateTemplateForm
import gophish.presentation.events.EmailTemplatesEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailFormTemplate(
    form: CreateTemplateForm,
    onEvent: (EmailTemplatesEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(EmailTemplatesEvent.ChangeFormVisibility)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = AppRes.string.text_mode)
            Switch(
                checked = form.isHTML,
                onCheckedChange = {
                    EmailTemplatesEvent.ChangeFormMode
                }
            )
            Text(text = AppRes.string.html_mode)

        }
        Column(modifier = Modifier.fillMaxSize(0.9f)) {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.name,
                onValueChange = {
                    onEvent(EmailTemplatesEvent.UpdateName(it))
                },
                hintText = AppRes.string.template_name,
                errorMessage = form.nameError
            )
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.subject,
                onValueChange = {
                    onEvent(EmailTemplatesEvent.UpdateSubject(it))
                }
            )
            if (form.isHTML) {
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.html,
                    onValueChange = {
                        onEvent(EmailTemplatesEvent.UpdateHtml(it))
                    }
                )
            } else {
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    text = form.text,
                    onValueChange = {
                        onEvent(EmailTemplatesEvent.UpdateText(it))
                    }
                )
            }
        }
    }
}