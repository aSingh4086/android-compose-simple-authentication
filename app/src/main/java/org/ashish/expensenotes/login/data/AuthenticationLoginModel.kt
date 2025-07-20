package org.ashish.expensenotes.login.data

data class AuthenticationLoginModel(
    val email: String = "",
    val password: String = "",
) {
    fun isValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}