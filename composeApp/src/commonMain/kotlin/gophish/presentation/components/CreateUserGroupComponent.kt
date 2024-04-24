package gophish.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import gophish.presentation.domain.CreateTargetForm
import gophish.presentation.domain.CreateUserGroupForm
import gophish.presentation.events.CreateUserGroupEvent
import group.domain.repository.UserGroupRepository
import home.domain.model.ApiCallResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class CreateUserGroupComponent(val componentContext: ComponentContext) :
    KoinScopeComponent, ComponentContext by componentContext {
    override val scope: Scope by lazy { getKoin().getScope("gophishComponents") }

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _createUserGroupForm = mutableStateOf(CreateUserGroupForm())
    val createUserGroupForm: State<CreateUserGroupForm> = _createUserGroupForm

    private val _createTargetForm = mutableStateOf(CreateTargetForm())
    val createTargetForm: State<CreateTargetForm> = _createTargetForm

    private val _apiCallResult = Channel<ApiCallResult>()
    val apiCallResult = _apiCallResult.receiveAsFlow()

    private val userGroupRepository = scope.get<UserGroupRepository>()

    fun onEvent(createUserGroupEvent: CreateUserGroupEvent) {
        when (createUserGroupEvent) {
            is CreateUserGroupEvent.AddTargetToGroup -> {
                addTarget()
            }

            is CreateUserGroupEvent.UpdateEmail -> {
                _createTargetForm.value =
                    _createTargetForm.value.copy(email = createUserGroupEvent.email)
            }

            is CreateUserGroupEvent.UpdateFirstName -> {
                _createTargetForm.value =
                    _createTargetForm.value.copy(firstname = createUserGroupEvent.firstName)
            }

            is CreateUserGroupEvent.UpdateLastName -> {
                _createTargetForm.value =
                    _createTargetForm.value.copy(lastName = createUserGroupEvent.lastName)
            }

            is CreateUserGroupEvent.UpdateName -> {
                _createUserGroupForm.value =
                    _createUserGroupForm.value.copy(name = createUserGroupEvent.name)
            }

            is CreateUserGroupEvent.UpdatePosition -> {
                _createTargetForm.value =
                    _createTargetForm.value.copy(position = createUserGroupEvent.position)
            }

            is CreateUserGroupEvent.AddUserGroup -> {
                addUserGroup()
            }
        }
    }

    private fun addUserGroup() {
        coroutineScope.launch {
            val result = userGroupRepository.createGroup(createUserGroupForm.value.toCreateForm())
            withContext(Dispatchers.Main) {
                _apiCallResult.send(result)
            }
        }
    }

    private fun addTarget() {
        _createUserGroupForm.value =
            _createUserGroupForm.value.copy(targets = _createUserGroupForm.value.targets + _createTargetForm.value.toTarget())
    }
}
