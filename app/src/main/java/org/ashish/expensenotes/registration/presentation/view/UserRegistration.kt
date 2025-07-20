package org.ashish.expensenotes.registration.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.ashish.expensenotes.components.Loader
import org.ashish.expensenotes.components.PasswordInput
import org.ashish.expensenotes.components.TextInput
import org.ashish.expensenotes.components.inlineMessages.InlineMessageView
import org.ashish.expensenotes.registration.model.UserRegistrationScreenState
import org.ashish.expensenotes.registration.presentation.viewModel.UserRegistrationViewModel

@Composable
fun UserRegistrationView() {

    val firebaseInstance = FirebaseFirestore.getInstance()
    val registrationViewModel: UserRegistrationViewModel =
        viewModel(factory = UserRegistrationViewModel.create(firebaseStorage = firebaseInstance))
    val uiState by registrationViewModel.userScreenState.collectAsStateWithLifecycle()
    val registrationState by registrationViewModel.registrationState.collectAsStateWithLifecycle()

    val screenMessage by registrationViewModel.screenMessage.collectAsStateWithLifecycle()

    when (uiState) {
        is UserRegistrationScreenState.Loading -> {
            Loader()
        }

        is UserRegistrationScreenState.Error,
        is UserRegistrationScreenState.Idle,
        is UserRegistrationScreenState.Success -> {
            LazyColumn(
                modifier = Modifier.padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (uiState is UserRegistrationScreenState.Error) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            InlineMessageView(screenMessage)
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier.fillParentMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Welcome! Please register to continue.",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                item {
                    TextInput(
                        value = registrationState.firstName,
                        onValueChange = {
                            registrationViewModel.updateRegistrationState(
                                registrationModel = registrationState.copy(firstName = it)
                            )
                        },
                        label = "First Name"
                    )
                }
                item {
                    TextInput(
                        value = registrationState.lastName,
                        onValueChange = {
                            registrationViewModel.updateRegistrationState(
                                registrationModel = registrationState.copy(lastName = it)
                            )
                        },
                        label = "Last Name"
                    )
                }

                item {
                    TextInput(
                        value = registrationState.userName,
                        onValueChange = {
                            registrationViewModel.updateRegistrationState(
                                registrationModel = registrationState.copy(userName = it)
                            )
                        },
                        label = "Username"
                    )
                }

                item {
                    PasswordInput(
                        value = registrationState.password,
                        onValueChange = {
                            registrationViewModel.updateRegistrationState(
                                registrationModel = registrationState.copy(password = it)
                            )
                        },
                        label = "Password"
                    )
                }

                item {
                    PasswordInput(
                        value = registrationState.confirmPassword,
                        onValueChange = {
                            registrationViewModel.updateRegistrationState(
                                registrationModel = registrationState.copy(confirmPassword = it)
                            )
                        },
                        label = "Re-enter Password"
                    )
                }
                item {
                    Box(
                        modifier = Modifier.fillParentMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {
                            registrationViewModel.register()
                        }) {
                            Text(
                                text = "Register",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AuthenticationLoginViewPreview() {
    UserRegistrationView()
}