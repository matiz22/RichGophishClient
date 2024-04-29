package gophish.user_groups.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.user_groups.domain.CreateTargetForm

@Composable
fun TargetFormItem(
    modifier: Modifier = Modifier,
    form: CreateTargetForm,
    onFirstNameUpdate: (String) -> Unit,
    onLastNameUpdate: (String) -> Unit,
    onPositionUpdate: (String) -> Unit,
    onEmailUpdate: (String) -> Unit,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.firstname,
                onValueChange = {
                    onFirstNameUpdate(it)
                },
                hintText = AppRes.string.target_first_name
            )
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.lastName,
                onValueChange = {
                    onLastNameUpdate(it)
                },
                hintText = AppRes.string.target_last_name
            )
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.email,
                onValueChange = {
                    onEmailUpdate(it)
                },
                hintText = AppRes.string.target_email
            )
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.position,
                onValueChange = {
                    onPositionUpdate(it)
                },
                hintText = AppRes.string.target_position
            )
        }
    }

}