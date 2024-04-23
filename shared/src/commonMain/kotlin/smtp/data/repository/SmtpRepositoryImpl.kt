package smtp.data.repository

import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult
import smtp.data.remote.SmtpApi
import smtp.domain.model.CreateSmtp
import smtp.domain.model.Smtp
import smtp.domain.repository.SmtpRepository

class SmtpRepositoryImpl(private val gophishHttpRequester: GophishHttpRequester) : SmtpRepository {
    private val smtpApi = SmtpApi(gophishHttpRequester)
    override suspend fun getSmtpProfiles(): DataOrError<List<Smtp>> {
        return smtpApi.getSmtpProfiles()
    }

    override suspend fun getSmtp(id: Long): DataOrError<Smtp> {
        return smtpApi.getSmtp(id)
    }

    override suspend fun createSmtp(createSmtp: CreateSmtp): ApiCallResult {
        return smtpApi.createSmtp(createSmtp)
    }

    override suspend fun modifySmtp(id: Long, createSmtp: CreateSmtp): ApiCallResult {
        return smtpApi.modifySmtp(id, createSmtp)
    }

    override suspend fun deleteSmtp(id: Long): ApiCallResult {
        return smtpApi.deleteSmtp(id)
    }

}