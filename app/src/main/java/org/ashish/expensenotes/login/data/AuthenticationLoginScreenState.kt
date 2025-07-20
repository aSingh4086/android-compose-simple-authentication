package org.ashish.expensenotes.login.data

sealed class AuthenticationLoginScreenState {
    data object Loading : AuthenticationLoginScreenState()
    data object Idle: AuthenticationLoginScreenState()
    data object Success : AuthenticationLoginScreenState()
    data object Error : AuthenticationLoginScreenState()
}