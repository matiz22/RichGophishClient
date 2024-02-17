package home.domain.use_case

import com.matiz22.richgophishclient.shared.SharedRes
import home.domain.model.ValidationResult

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        return if (password.length > 15) {
            ValidationResult(successful = true)
        } else {
            ValidationResult(
                successful = false,
                errorMessage = SharedRes.string.password_length_error
            )
        }
    }
}