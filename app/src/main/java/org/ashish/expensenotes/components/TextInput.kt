package org.ashish.expensenotes.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.ashish.expensenotes.R


@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: String
) {
    TextField(
        label = {
            Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(label)
                Text(text = "*", color = Color.Red)
            }

        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(),
        onValueChange = onValueChange,
        value = value
    )
}