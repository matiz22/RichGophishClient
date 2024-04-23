package page.data.repository

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult
import page.data.remote.PagesApi
import page.domain.model.CreatePage
import page.domain.model.Page
import page.domain.repository.PagesRepository

class PageRepositoryImpl(private val gophishHttpRequester: GophishHttpRequester) : PagesRepository {
    private val pageApi = PagesApi(gophishHttpRequester)
    override suspend fun getPages(): DataOrError<List<Page>> {
        return pageApi.getPages()
    }

    override suspend fun getPage(id: Long): DataOrError<Page> {
        return pageApi.getPage(id)
    }

    override suspend fun createPage(createPage: CreatePage): ApiCallResult {
        return pageApi.createPage(createPage)
    }

    override suspend fun modifyPage(modifyPage: CreatePage): ApiCallResult {
        return pageApi.modifyPage(modifyPage)
    }

    override suspend fun deletePage(id: Long): ApiCallResult {
        return pageApi.deletePage(id)
    }
}