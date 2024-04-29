package gophish.user_groups.events

import group.domain.model.Group
import group.domain.model.Target

sealed class UserGroupListEvent {
    data class PickUserGroup(val target: List<Target>): UserGroupListEvent()
    data class DeleteGroup(val group: Group): UserGroupListEvent()
    data object UnPickUserGroup : UserGroupListEvent()
}