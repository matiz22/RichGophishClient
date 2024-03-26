package campaigns.data.repository

import campaigns.data.remote.CampaignApi
import campaigns.data.remote.GophishHttpRequester
import campaigns.domain.model.Campaign
import campaigns.domain.model.CampaignResult
import campaigns.domain.model.CampaignSummary
import campaigns.domain.model.CreateCampaign
import campaigns.domain.model.DataOrError
import campaigns.domain.repository.CampaignRepository
import configs.domain.use_case.ValidateApiKey
import home.domain.model.ApiCallResult

class CampaignRepositoryImpl(private val gophishHttpRequester: GophishHttpRequester) :
    CampaignRepository {

    private val campaignApi = CampaignApi(gophishHttpRequester)
    override suspend fun getCampaigns(): DataOrError<List<Campaign>> {
        return campaignApi.getCampaigns()
    }

    override suspend fun getCampaign(id: Long): DataOrError<Campaign> {
        return campaignApi.getCampaign(id)
    }

    override suspend fun createCampaign(createCampaign: CreateCampaign): ApiCallResult {
        return campaignApi.createCampaign(createCampaign)
    }

    override suspend fun getCampaignResults(id: Long): DataOrError<CampaignResult> {
        return campaignApi.getCampaignResult(id)
    }

    override suspend fun getCampaignSummary(id: Long): DataOrError<CampaignSummary> {
        return campaignApi.getCampaignSummary(id)
    }

    override suspend fun deleteCampaign(id: Long): ApiCallResult {
        return campaignApi.deleteCampaign(id)
    }

    override suspend fun completeCampaign(id: Long): ApiCallResult {
        return campaignApi.completeCampaign(id)
    }

}