package org.ashish.expensenotes.login.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ashish.expensenotes.components.inlineMessages.data.InlineMessageModel
import org.ashish.expensenotes.components.inlineMessages.data.MessageSeverity
import org.ashish.expensenotes.login.data.AuthenticationLoginModel
import org.ashish.expensenotes.login.data.AuthenticationLoginScreenState

class AuthenticationLoginViewModel(private val firebaseFireStore: FirebaseFirestore) : ViewModel() {
    private val _loginScreenState = MutableStateFlow<AuthenticationLoginScreenState>(
        AuthenticationLoginScreenState.Idle
    )
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _authenticationLoginData = MutableStateFlow<AuthenticationLoginModel>(
        AuthenticationLoginModel(
            email = "",
            password = ""
        )
    )
    val authenticationLoginData = _authenticationLoginData.asStateFlow()

    private val _userMessages = MutableStateFlow<List<InlineMessageModel>>(listOf())
    val userMessages = _userMessages.asStateFlow()

    fun updateAuthenticationLoginData(authenticationLoginModel: AuthenticationLoginModel) {
        _authenticationLoginData.value = authenticationLoginModel
    }

    fun login() {
        viewModelScope.launch {
            firebaseFireStore.collection("users").get().addOnSuccessListener { result ->
                _loginScreenState.value = AuthenticationLoginScreenState.Success
                val isValidUserNameAndPassword = result.documents.any { document ->
                    val email = document.getString("email")
                    val password = document.getString("password")
                    email == _authenticationLoginData.value.email && password == _authenticationLoginData.value.password
                }
                _loginScreenState.value = if (isValidUserNameAndPassword) {
                    AuthenticationLoginScreenState.Success
                } else {
                    AuthenticationLoginScreenState.Error

                }
                _userMessages.value = listOf(
                    InlineMessageModel(
                        message = "Invalid email or password",
                        severity = MessageSeverity.ERROR.severity
                    )
                )
            }.addOnFailureListener {
                _loginScreenState.value = AuthenticationLoginScreenState.Error
            }
        }

    }

    companion object {
        fun create(firebaseFireStore: FirebaseFirestore): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(AuthenticationLoginViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return AuthenticationLoginViewModel(firebaseFireStore) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}