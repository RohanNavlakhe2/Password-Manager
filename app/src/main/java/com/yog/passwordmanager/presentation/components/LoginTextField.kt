package com.yog.passwordmanager.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.yog.passwordmanager.ui.theme.Dimens.errorTextSpacing

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    hint: String,
    shouldShowError:Boolean,
    errorText:String
) {
    Column() {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = hint)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.primary,
                backgroundColor = Color.White
            ),
        )
        Spacer(Modifier.height(errorTextSpacing))
        if(shouldShowError) Text(errorText, color = MaterialTheme.colors.error)
    }

}