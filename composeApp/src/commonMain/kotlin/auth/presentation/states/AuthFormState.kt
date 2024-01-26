package auth.presentation.states

data class AuthFormState(
    val isNewUser:Boolean = true,
    val email:String = "",
    val emailError:String? = null,
    val password:String = "",
    val passwordError:String? = null ,
    val otherErrors:String? = null
)
