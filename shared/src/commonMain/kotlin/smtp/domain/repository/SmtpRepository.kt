package smtp.domain.repository

import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult
import smtp.domain.model.CreateSmtp
import smtp.domain.model.Smtp

interface SmtpRepository {
    suspend fun getSmtpProfiles(): DataOrError<List<Smtp>>
    suspend fun getSmtp(id: Long): DataOrError<Smtp>
    suspend fun createSmtp(createSmtp: CreateSmtp): ApiCallResult
    suspend fun modifySmtp(id: Long, createSmtp: CreateSmtp): ApiCallResult
    suspend fun deleteSmtp(id: Long): ApiCallResult
}