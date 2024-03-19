package campaigns.domain.repository

import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignResult
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.CreateCampaign
import campaigns.domain.model.DataOrError
import home.domain.model.ApiCallResult

interface CampaignRepository {
    suspend fun getCampaigns(): DataOrError< List<Campaign>>
    suspend fun getCampaign(id: Long): DataOrError<Campaign>
    suspend fun createCampaign(createCampaign: CreateCampaign): ApiCallResult
    suspend fun getCampaignResults(id: Long): DataOrError<CampaignResult>
    suspend fun getCampaignSummary(id: Long): DataOrError<CampaignSummary>
    suspend fun deleteCampaign(id: Long): ApiCallResult
    suspend fun completeCampaign(id: Long): ApiCallResult
}