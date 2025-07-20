package org.ashish.expensenotes.login.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.ashish.expensenotes.components.Loader
import org.ashish.expensenotes.components.PasswordInput
import org.ashish.expensenotes.components.TextInput
import org.ashish.expensenotes.login.data.AuthenticationLoginScreenState
import org.ashish.expensenotes.login.presentation.viewModel.AuthenticationLoginViewModel

@Composable
fun AuthenticationLoginView() {

    val firebaseStorage = FirebaseFirestore.getInstance()
    val authenticationLoginViewModel: AuthenticationLoginViewModel =
        viewModel(factory = AuthenticationLoginViewModel.create(firebaseFireStore = firebaseStorage))
    val authenticationLoginData =
        authenticationLoginViewModel.authenticationLoginData.collectAsStateWithLifecycle()

    val loginScreenState by
    authenticationLoginViewModel.loginScreenState.collectAsStateWithLifecycle()

    when (loginScreenState) {
        is AuthenticationLoginScreenState.Idle,
        is AuthenticationLoginScreenState.Success,
        is AuthenticationLoginScreenState.Error -> {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(10.dp)
            ) {
                item {
                    TextInput(
                        value = "",
                        onValueChange = {
                            authenticationLoginViewModel.updateAuthenticationLoginData(
                                authenticationLoginData.value.copy(email = it)
                            )
                        },
                        label = "Email",
                        modifier = Modifier
                    )

                }
                item {
                    PasswordInput(
                        value = "",
                        onValueChange = {
                            authenticationLoginViewModel.updateAuthenticationLoginData(
                                authenticationLoginData.value.copy(password = it)
                            )
                        },
                        label = "Password",
                        isMaskingEnabled = true,
                    )
                }

                item {
                    Button(onClick = {
                        authenticationLoginViewModel.login()
                    }) {
                        Text(text = "Login")
                    }

                }
            }
        }

        else -> {
            Loader()
        }
    }



}

@Preview
@Composable
fun AuthenticationLoginViewPreview() {
    AuthenticationLoginView()
}