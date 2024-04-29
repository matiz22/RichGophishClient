package gophish.user_groups.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import campaigns.domain.model.DataOrError
import gophish.user_groups.composables.TargetColumn
import gophish.user_groups.composables.UserGroupCard
import gophish.user_groups.events.UserGroupListEvent
import group.domain.model.Group
import group.domain.model.Target

@Composable
fun UserGroupsListScreen(
    userGroups: DataOrError<List<Group>>,
    pickedTargets: List<Target>?,
    onEvent: (UserGroupListEvent) -> Unit,
) {
    if (pickedTargets != null) {
        Dialog(
            onDismissRequest = {
                onEvent(UserGroupListEvent.UnPickUserGroup)
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            TargetColumn(pickedTargets) { onEvent(UserGroupListEvent.UnPickUserGroup) }
        }
    }

    if (userGroups.data != null) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(4.dp),
            columns = GridCells.FixedSize(500.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(userGroups.data!!) { group ->
                UserGroupCard(
                    modifier = Modifier.width(500.dp),
                    group = group,
                    onClick = {
                        onEvent(UserGroupListEvent.PickUserGroup(group.targets))
                    },
                    delete = {
                        onEvent(UserGroupListEvent.DeleteGroup(group))
                    }
                )
            }
        }
    }
}