package configs.domain.use_case

import com.matiz22.richgophishclient.shared.SharedRes
import home.domain.model.ValidationResult

class ValidateApiKey {
    fun execute(apiKey: String): ValidationResult {
        return if (apiKey.isNotEmpty()) {
            ValidationResult(
                successful = true,
            )
        } else {
            ValidationResult(
                successful = false,
                errorMessage = SharedRes.string.api_key_length_error
            )
        }
    }
}