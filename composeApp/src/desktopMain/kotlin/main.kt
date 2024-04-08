import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.datlag.kcef.KCEF
import helper.ApplicationState
import helper.HtmlViewerWindow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import navigation.RootComponent
import root.di.initKoin

import utils.runOnUiThread
import java.io.File
import kotlin.math.max

val applicationState by mutableStateOf(ApplicationState())

fun main() {
    initKoin()
    val lifecycle = LifecycleRegistry()
    val root = runOnUiThread {
        RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
        )
    }
    application {
        var restartRequired by remember { mutableStateOf(false) }
        var downloading by remember { mutableStateOf(0F) }
        var initialized by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                KCEF.init(builder = {
                    release("jbr-release-17.0.10b1087.23")
                    installDir(File("kcef-bundle"))
                    progress {
                        onDownloading {
                            downloading = max(it, 0F)
                        }
                        onInitialized {
                            initialized = true
                        }
                    }
                    settings {
                        cachePath = File("cache").absolutePath
                    }
                }, onError = {
                    it?.printStackTrace()
                }, onRestartRequired = {
                    restartRequired = true
                })
            }
        }
        if (initialized) {
            for (window in applicationState.htmlWindows) {
                key(window) {
                    this.HtmlViewerWindow(window)
                }
            }
        }
        DisposableEffect(Unit) {
            onDispose {
                KCEF.disposeBlocking()
            }
        }
        Window(
            onCloseRequest = ::exitApplication
        ) {
            App(root = root)
        }
    }
}

