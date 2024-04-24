package root.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import config.presentation.states.IconButtonState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    content: @Composable() (() -> Unit)? = null,
    sideBarContent: @Composable() (() -> Unit)? = null,
    leadingIconButtonState: IconButtonState?,
    snackbarHostState: SnackbarHostState? = null,
    floatingActionButtonState: IconButtonState?
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                navigationIcon = {
                    if (leadingIconButtonState != null)
                        IconButton(
                            onClick = {
                                leadingIconButtonState.action()
                            },
                            content = {
                                Icon(
                                    imageVector = leadingIconButtonState.icon,
                                    contentDescription = null
                                )
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                }
            )
        },
        floatingActionButton = {
            if (floatingActionButtonState != null) {
                FloatingActionButton(
                    onClick = {
                        floatingActionButtonState.action()
                    },
                    content = {
                        Icon(
                            imageVector = floatingActionButtonState.icon,
                            contentDescription = null
                        )
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            if (snackbarHostState != null) {
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    ) { paddingValues ->
        if (content != null) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues)) {
                content()
            }
        }
    }
}
