package configs.domain.use_case

import com.matiz22.richgophishclient.shared.SharedRes
import home.domain.model.ValidationResult

class ValidateName {
    fun execute(name: String): ValidationResult {
        return if (name.isNotEmpty()) {
            ValidationResult(
                successful = true,
            )
        } else {
            ValidationResult(
                successful = false,
                errorMessage = SharedRes.string.name_length_error
            )
        }
    }
}