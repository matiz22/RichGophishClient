package template.domain.repository

import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult
import template.domain.model.CreateTemplate
import template.domain.model.Template

interface TemplateRepository {
    suspend fun getTemplates(): DataOrError<List<Template>>
    suspend fun getTemplate(id: Long): DataOrError<Template>
    suspend fun createTemplate(createTemplate: CreateTemplate): ApiCallResult
    suspend fun modifyTemplate(createTemplate: CreateTemplate): ApiCallResult
    suspend fun deleteTemplate(id: Long): ApiCallResult
}