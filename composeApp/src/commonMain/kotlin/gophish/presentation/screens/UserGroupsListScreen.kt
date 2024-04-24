package gophish.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import campaigns.domain.model.DataOrError
import gophish.presentation.composables.UserGroupCard
import group.domain.model.Group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserGroupsListScreen(
    userGroups: DataOrError<List<Group>>,
    navigateToDetails: (Group) -> Unit,
    pickedGroup: Group?,
    unPickGroup: () -> Unit,
) {
    if (pickedGroup != null){
        AlertDialog(
            onDismissRequest = {
                unPickGroup()
            }
        ){

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
                        navigateToDetails(group)
                    }
                )
            }
        }
    }
}