package template.data.repository

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult
import template.data.remote.TemplateApi
import template.domain.model.CreateTemplate
import template.domain.model.Template
import template.domain.repository.TemplateRepository

class TemplatesRepositoryImpl(private val gophishHttpRequester: GophishHttpRequester) :
    TemplateRepository {
    private val templateApi = TemplateApi(gophishHttpRequester)
    override suspend fun getTemplates(): DataOrError<List<Template>> {
        return templateApi.getTemplates()
    }

    override suspend fun getTemplate(id: Long): DataOrError<Template> {
        return templateApi.getTemplate(id)
    }

    override suspend fun createTemplate(createTemplate: CreateTemplate): ApiCallResult {
        return templateApi.createTemplate(createTemplate)
    }

    override suspend fun modifyTemplate(createTemplate: CreateTemplate): ApiCallResult {
        return templateApi.modifyTemplate(createTemplate)
    }

    override suspend fun deleteTemplate(id: Long): ApiCallResult {
        return templateApi.deleteTemplate(id)
    }

}