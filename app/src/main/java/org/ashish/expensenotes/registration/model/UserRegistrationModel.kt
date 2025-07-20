package org.ashish.expensenotes.registration.model

import java.security.SecureRandom
import java.util.UUID
import kotlin.math.abs

data class UserRegistrationModel(
    val id: Long = abs(SecureRandom().nextLong()),
    val firstName: String = "",
    val lastName: String = "",
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)

sealed class UserRegistrationScreenState {
    data class Loading(val message: String) : UserRegistrationScreenState()
    data class Success(val message: String) : UserRegistrationScreenState()
    data class Error(val message: String) : UserRegistrationScreenState()
    data class Idle(val message: String) : UserRegistrationScreenState()
}