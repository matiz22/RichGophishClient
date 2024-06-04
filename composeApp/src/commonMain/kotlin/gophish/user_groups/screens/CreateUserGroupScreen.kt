package gophish.user_groups.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.user_groups.composables.TargetCard
import gophish.user_groups.composables.TargetFormItem
import gophish.user_groups.domain.CreateTargetForm
import gophish.user_groups.domain.CreateUserGroupForm
import gophish.user_groups.events.CreateUserGroupEvent

@Composable
fun CreateUserGroupScreen(
    form: CreateUserGroupForm,
    onEvent: (CreateUserGroupEvent) -> Unit,
    createTargetForm: CreateTargetForm,
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                text = form.name,
                onValueChange = {
                    onEvent(CreateUserGroupEvent.UpdateName(it))
                },
                hintText = AppRes.string.group_hint
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    onEvent(CreateUserGroupEvent.AddUserGroup)
                }) {
                    Text(text = AppRes.string.group_add_group)
                }
            }
        }

        item {
            TargetFormItem(
                modifier = Modifier,
                form = createTargetForm,
                onFirstNameUpdate = {
                    onEvent(CreateUserGroupEvent.UpdateFirstName(it))
                },
                onLastNameUpdate = {
                    onEvent(CreateUserGroupEvent.UpdateLastName(it))
                },
                onEmailUpdate = {
                    onEvent(CreateUserGroupEvent.UpdateEmail(it))
                },
                onPositionUpdate = {
                    onEvent(CreateUserGroupEvent.UpdatePosition(it))
                },
            )
        }

        item {
            OutlinedButton(onClick = {
                onEvent(CreateUserGroupEvent.SubmitTarget)
            }) {
                Text(text = AppRes.string.group_add_to_the_group)
            }
        }

        items(form.targets) { target ->
            TargetCard(
                modifier = Modifier,
                target = target
            ) {
                onEvent(CreateUserGroupEvent.DeleteTarget(target))
            }
        }
    }
}


