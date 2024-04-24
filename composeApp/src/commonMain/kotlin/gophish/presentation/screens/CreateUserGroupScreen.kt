package gophish.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import auth.presentation.composables.InputField
import com.matiz22.richgophishclient.AppRes
import gophish.presentation.composables.TargetCard
import gophish.presentation.domain.CreateTargetForm
import gophish.presentation.domain.CreateUserGroupForm
import gophish.presentation.events.CreateUserGroupEvent

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
                hintText = AppRes.string.name_hint
            )
        }
        items(form.targets) { target ->
            TargetCard(
                modifier = Modifier,
                target = target,
                onDelete = {
                    onEvent(CreateUserGroupEvent.DeleteTarget(target))
                }
            )
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
    }
}


