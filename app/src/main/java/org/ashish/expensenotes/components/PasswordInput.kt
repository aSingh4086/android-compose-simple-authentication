package org.ashish.expensenotes.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Password",
    isMaskingEnabled: Boolean = true,
) {
    TextInput(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = Modifier,
        visualTransformation = if (isMaskingEnabled) PasswordVisualTransformation() else VisualTransformation.None
    )
}