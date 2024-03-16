package home.data.validators

class UrlValidator {
    companion object {
        private val regex = Regex(
            """^(?:(http|https)://)?(?:(?:([A-Za-z0-9]+(?:-[A-Za-z0-9]+)*\.)+[A-Za-z]{2,})|(?:\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}))(?:[:\d]*)?(?:/(?:[^\s]*)?)?$"""
        )

        fun isValidUrlPattern(url: String): Boolean {
            return url.matches(regex)
        }
    }
}