package gophish.smtp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.smtp.domain.CreateHeaderForm
import gophish.smtp.domain.CreateSmtpForm
import gophish.smtp.events.CreateSmtpEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSmtpScreen(
    form: CreateSmtpForm,
    headerForm: CreateHeaderForm,
    onEvent: (CreateSmtpEvent) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(), text = form.name, onValueChange = {
                    onEvent(CreateSmtpEvent.UpdateName(it))
                }, hintText = AppRes.string.name_hint, errorMessage = form.nameError
            )
        }
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(), text = form.fromAddress, onValueChange = {
                    onEvent(CreateSmtpEvent.UpdateFromAddress(it))
                }, hintText = AppRes.string.smtp_from_address
            )
        }
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(), text = form.host, onValueChange = {
                    onEvent(CreateSmtpEvent.UpdateHost(it))
                }, hintText = AppRes.string.smtp_host, errorMessage = form.hostError
            )
        }
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(), text = form.username, onValueChange = {
                    onEvent(CreateSmtpEvent.UpdateUsername(it))
                }, hintText = AppRes.string.smtp_username
            )
        }
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(), text = form.password, onValueChange = {
                    onEvent(CreateSmtpEvent.UpdatePassword(it))
                }, hintText = AppRes.string.smtp_password
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = {
                    onEvent(CreateSmtpEvent.AddHeader)
                }) {
                    Text(text = AppRes.string.smtp_add_header)
                }
                Button(onClick = {
                    onEvent(CreateSmtpEvent.Submit)
                }) {
                    Text(text = AppRes.string.smtp_add_profile)
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputField(
                    modifier = Modifier.weight(0.5f), text = headerForm.key, onValueChange = {
                        onEvent(CreateSmtpEvent.UpdateHeaderKey(it))
                    }, hintText = AppRes.string.header_key
                )
                InputField(
                    modifier = Modifier.weight(0.5f), text = headerForm.value, onValueChange = {
                        onEvent(CreateSmtpEvent.UpdateHeaderValue(it))
                    }, hintText = AppRes.string.header_value
                )
            }
        }
        items(form.headers) { header ->
            OutlinedCard(modifier = Modifier.padding(16.dp), onClick = {
                onEvent(CreateSmtpEvent.DeleteHeader(header))
            }) {
                Text(modifier = Modifier.padding(16.dp), text = "${header.key}: ${header.value}")
            }
        }
    }
}


