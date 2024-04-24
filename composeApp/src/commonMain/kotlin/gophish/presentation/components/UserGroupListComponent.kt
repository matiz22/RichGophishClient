package gophish.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.domain.model.DataOrError
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.presentation.events.UserGroupListEvent
import group.domain.model.Group
import group.domain.repository.UserGroupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.scope.Scope

class UserGroupListComponent(componentContext: ComponentContext) :
    KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }
    private val userGroupRepository by inject<UserGroupRepository>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _userGroups: MutableStateFlow<DataOrError<List<Group>>> =
        MutableStateFlow(
            DataOrError()
        )
    val userGroups = _userGroups.asStateFlow()

    var pickedGroup by mutableStateOf<Group?>(null)

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
        updateTemplates()
    }

    private fun updateTemplates() {
        coroutineScope.launch {
            _userGroups.emit(userGroupRepository.getGroups())
        }
    }

    fun onEvent(userGroupListEvent: UserGroupListEvent){
        pickedGroup = when(userGroupListEvent){
            is UserGroupListEvent.PickUserGroup -> {
                userGroupListEvent.group
            }

            is UserGroupListEvent.UnPickUserGroup -> {
                null
            }
        }
    }

}
