package gophish.user_groups.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import campaigns.domain.model.DataOrError
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import gophish.user_groups.events.UserGroupListEvent
import group.domain.model.Group
import group.domain.model.Target
import group.domain.repository.UserGroupRepository
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    var pickedGroup by mutableStateOf<List<Target>?>(null)

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    init {
        lifecycle.doOnDestroy {
            coroutineScope.cancel()
        }
        updateUserGroups()
    }

    fun updateUserGroups() {
        coroutineScope.launch {
            _userGroups.emit(userGroupRepository.getGroups())
        }
    }

    fun onEvent(userGroupListEvent: UserGroupListEvent){
        when(userGroupListEvent){
            is UserGroupListEvent.PickUserGroup -> {
                pickedGroup =  userGroupListEvent.target
            }

            is UserGroupListEvent.UnPickUserGroup -> {
                pickedGroup = null
            }

            is UserGroupListEvent.DeleteGroup ->{
                coroutineScope.launch {
                    val result = deleteUserGroup(userGroupListEvent.group).await()
                    if (result.successful) {
                        updateUserGroups()
                    }
                }

            }

        }
    }

    private fun deleteUserGroup(group: Group): Deferred<ApiCallResult> {
        return coroutineScope.async {
            val result = userGroupRepository.deleteGroup(group.id)
            withContext(Dispatchers.Main) {
                _apiCallResult.send(result)
            }
            result
        }
    }

}
