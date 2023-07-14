package com.yog.passwordmanager.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.presentation.components.PasswordManagerPasswordTextField
import com.yog.passwordmanager.presentation.components.PasswordManagerTextField
import com.yog.passwordmanager.presentation.state.TextFieldState
import com.yog.passwordmanager.presentation.viewmodel.AddUpdatePasswordViewModel


@Composable
fun AddUpdatePasswordScreen(
    viewModel: AddUpdatePasswordViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(25.dp)
            .border(1.dp, MaterialTheme.colors.primary,RoundedCornerShape(10.dp))
            .padding(20.dp)
    ) {

        val titleFieldState = viewModel.titleState
        val passwordFieldState = viewModel.passwordState

        PasswordManagerTextField(
            value = titleFieldState.value.text,
            onValueChange = {
                viewModel.onEvent(
                    AddUpdatePasswordViewModel.AddUpdatePasswordEvent.TitleTextChanged(
                        it
                    )
                )
            },
            hint = titleFieldState.value.hint,
        )

        Spacer(modifier = Modifier.height(15.dp))

        PasswordManagerPasswordTextField(
            value = passwordFieldState.value.text,
            onValueChange = {
                viewModel.onEvent(
                    AddUpdatePasswordViewModel.AddUpdatePasswordEvent.PasswordTextChanged(
                        it
                    )
                )
            },
            hint = passwordFieldState.value.hint,
            isPasswordVisible = passwordFieldState.value.isPasswordVisible,
            onPasswordToggle = {
                viewModel.onEvent(
                    AddUpdatePasswordViewModel.AddUpdatePasswordEvent.PasswordToggle
                )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                viewModel.onEvent(
                    AddUpdatePasswordViewModel.AddUpdatePasswordEvent.InsertPasswordEntry
                )
                navController.navigateUp()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text("Save", color = Color.White)
        }


    }
}