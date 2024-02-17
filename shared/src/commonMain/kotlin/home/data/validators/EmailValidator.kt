package home.data.validators

class EmailValidator {
    companion object {

        private const val emailRegex =  "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        fun isValidEmailPattern(email: String): Boolean {
            val pattern = Regex(emailRegex)
            return pattern.matches(email)
        }
    }
}