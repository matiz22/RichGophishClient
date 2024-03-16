package home.domain.use_case

import com.matiz22.richgophishclient.shared.SharedRes
import home.data.validators.UrlValidator
import home.domain.model.ValidationResult

class ValidateUrl {
    fun execute(url: String): ValidationResult {
        return if (UrlValidator.isValidUrlPattern(url)) {
            ValidationResult(
                successful = true
            )
        }else{
            ValidationResult(
                successful = false,
                errorMessage = SharedRes.string.wrong_url_pattern
            )
        }
    }
}