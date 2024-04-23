package group.data.repository

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
import group.data.remote.UserGroupApi
import group.domain.model.CreateGroup
import group.domain.model.Group
import group.domain.model.ModifyGroup
import group.domain.repository.UserGroupRepository
import home.domain.model.ApiCallResult

class UserGroupRepositoryImpl(private val gophishHttpRequester: GophishHttpRequester) :
    UserGroupRepository {
        private val userGroupApi = UserGroupApi(gophishHttpRequester)
    override suspend fun getGroups(): DataOrError<List<Group>> {
        return userGroupApi.getGroups()
    }

    override suspend fun getGroup(id: Long): DataOrError<Group> {
        return userGroupApi.getGroup(id)
    }

    override suspend fun createGroup(createGroup: CreateGroup): ApiCallResult {
        return userGroupApi.createGroup(createGroup)
    }

    override suspend fun modifyGroup(modifyGroup: ModifyGroup): ApiCallResult {
        return userGroupApi.modifyGroup(modifyGroup)
    }

    override suspend fun deleteGroup(id: Long): ApiCallResult {
        return userGroupApi.deleteGroup(id)
    }
}