package group.domain.repository

import campaigns.domain.model.DataOrError
import group.domain.model.CreateGroup
import group.domain.model.Group
import group.domain.model.ModifyGroup
import home.domain.model.ApiCallResult

interface UserGroupRepository {
    suspend fun getGroups(): DataOrError<List<Group>>
    suspend fun getGroup(id: Long): DataOrError<Group>
    suspend fun createGroup(createGroup: CreateGroup): ApiCallResult
    suspend fun modifyGroup(modifyGroup: ModifyGroup): ApiCallResult
    suspend fun deleteGroup(id: Long): ApiCallResult
}