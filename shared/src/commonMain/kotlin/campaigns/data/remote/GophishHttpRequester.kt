package campaigns.data.remote

import home.data.remote.provideGophishHttpClient

class GophishHttpRequester(
    val host: String,
    private val apiKey: String
) {
    val httpClient = provideGophishHttpClient(apiKey)
}