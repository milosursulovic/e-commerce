package com.ecommerce.shopdaily.presentation.screens.login.components.custom_login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ecommerce.shopdaily.presentation.screens.login.util.custom_login.LoginFieldType
import com.ecommerce.shopdaily.presentation.ui.theme.Gray
import com.ecommerce.shopdaily.presentation.ui.theme.Success

@Composable
fun LoginTextField(
    type: LoginFieldType,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        singleLine = true,
        value = text,
        shape = RoundedCornerShape(10.dp),
        onValueChange = {
            onValueChange(it)
        },
        trailingIcon = {
            if (type is LoginFieldType.Email && text.isNotEmpty()) {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Outlined.Check,
                        null,
                        modifier = Modifier.size(20.dp),
                        tint = Success
                    )
                }
            }
        },
        label = {
            Text(
                text = when (type) {
                    is LoginFieldType.Email -> "Email"
                    is LoginFieldType.Password -> "Password"
                }, color = MaterialTheme.colors.onSecondary
            )
        },
        visualTransformation = if (type is LoginFieldType.Email) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = {
            Text(
                text = when (type) {
                    is LoginFieldType.Email -> "Email"
                    is LoginFieldType.Password -> "Password"
                },
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSecondary
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedLabelColor = Gray
        )
    )
}