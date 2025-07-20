package org.ashish.expensenotes.registration.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ashish.expensenotes.components.inlineMessages.data.InlineMessageModel
import org.ashish.expensenotes.components.inlineMessages.data.MessageSeverity
import org.ashish.expensenotes.registration.model.UserRegistrationModel
import org.ashish.expensenotes.registration.model.UserRegistrationScreenState

class UserRegistrationViewModel(private val firebaseStorage: FirebaseFirestore) : ViewModel() {
    private val _registrationState = MutableStateFlow<UserRegistrationModel>(
        UserRegistrationModel(
            firstName = "",
            lastName = "",
            userName = "",
            email = "",
            password = "",
            confirmPassword = ""
        )
    )
    val registrationState = _registrationState.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    val _screenMessage = MutableStateFlow<List<InlineMessageModel>>(listOf())
    val screenMessage = _screenMessage.asStateFlow()

    private val _userScreenState = MutableStateFlow<UserRegistrationScreenState>(
        UserRegistrationScreenState.Success(
            message = "Loading user registration screen..."
        )
    )

    val userScreenState = _userScreenState.asStateFlow()

    fun updateRegistrationState(
        registrationModel: UserRegistrationModel
    ) {
        _registrationState.value = registrationModel
    }

    fun register() {
        _userScreenState.value = UserRegistrationScreenState.Loading(
            message = "Registering user..."
        )
        viewModelScope.launch {

            try {
                val docRef = firebaseStorage.collection("users")
                    .document(_registrationState.value.id.toString())
                firebaseStorage.runTransaction { transaction ->
                    val snapshot = transaction.get(docRef)
                    val user = snapshot.getString("userName")
                    if (user == _registrationState.value.userName) {
                        _userScreenState.value = UserRegistrationScreenState.Error(
                            message = "User with this username already exists."
                        )
                        _screenMessage.value = listOf(
                                InlineMessageModel(
                                    message = "User with this username already exists.",
                                    severity = MessageSeverity.ERROR.severity
                                )
                            )
                        throw Exception("User with this ID already exists.")
                    } else {
                        transaction.set(docRef, _registrationState.value)
                    }
                }
                    .addOnFailureListener { e ->
                        _screenMessage.value = listOf(
                            InlineMessageModel(
                                message = "Failed to register user: ${e.message}",
                                severity = MessageSeverity.ERROR.severity
                            )
                        )
                        _userScreenState.value = UserRegistrationScreenState.Error(
                            message = "Failed to register user: ${e.message}"
                        )
                    }.addOnSuccessListener {
                        _userScreenState.value = UserRegistrationScreenState.Success(
                            message = "User registered successfully!"
                        )
                    }


            } catch (e: Exception) {
                _screenMessage.value = listOf(
                    InlineMessageModel(
                        message = "Failed to register user: ${e.message}",
                        severity = MessageSeverity.ERROR.severity
                    )
                )
                _userScreenState.value = UserRegistrationScreenState.Error(
                    message = "Registration failed: ${e.message}"
                )
            }
        }
    }

    companion object {
        fun create(firebaseStorage: FirebaseFirestore): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(UserRegistrationViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return UserRegistrationViewModel(firebaseStorage) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}