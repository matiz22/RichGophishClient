package page.domain.repository

import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult
import page.domain.model.CreatePage
import page.domain.model.Page

interface PagesRepository {
    suspend fun getPages(): DataOrError<List<Page>>
    suspend fun getPage(id: Long): DataOrError<Page>
    suspend fun createPage(createPage: CreatePage): ApiCallResult
    suspend fun modifyPage(modifyPage: CreatePage): ApiCallResult
    suspend fun deletePage(id: Long): ApiCallResult
}