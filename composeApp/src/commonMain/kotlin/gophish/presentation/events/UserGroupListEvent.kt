package gophish.presentation.events

import group.domain.model.Group

sealed class UserGroupListEvent {
    data class PickUserGroup(val group: Group): UserGroupListEvent()
    data object UnPickUserGroup :UserGroupListEvent()
}