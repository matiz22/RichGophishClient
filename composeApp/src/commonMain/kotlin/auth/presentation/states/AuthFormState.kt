package auth.presentation.states

data class AuthFormState(
    var isNewUser:Boolean = true,
    var email:String = "",
    var emailError:String? = null,
    var password:String = "",
    var passwordError:String? = null,
    val otherErrors:String? = null
)
