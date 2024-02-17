package home.domain.use_case

import com.matiz22.richgophishclient.shared.SharedRes
import home.data.validators.EmailValidator
import home.domain.model.ValidationResult

class ValidateEmail {
    companion object {
        fun execute(email: String): ValidationResult {
            return if (EmailValidator.isValidEmailPattern(email)) {
                ValidationResult(successful = true)
            } else {
                ValidationResult(
                    successful = false,
                    errorMessage = SharedRes.string.email_pattern_error
                )
            }
        }
    }
}