package root.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
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
import com.matiz22.richgophishclient.AppRes
import config.presentation.states.FloatingActionButtonState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    content: @Composable() (() -> Unit)? = null,
    sideBarContent: @Composable() (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState? = null,
    floatingActionButtonState: FloatingActionButtonState?
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
                //TODO move navigationIcon to parameter of appScaffold
                navigationIcon = {
                    IconButton(
                        onClick = {

                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = AppRes.string.extend_sidebar
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
