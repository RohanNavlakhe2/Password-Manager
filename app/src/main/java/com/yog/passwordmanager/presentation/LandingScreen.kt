package com.yog.passwordmanager.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yog.passwordmanager.presentation.components.LoginPasswordTextField
import com.yog.passwordmanager.presentation.components.LoginTextField
import com.yog.passwordmanager.ui.theme.Dimens
import com.yog.passwordmanager.ui.theme.LoginBg

@Preview
@Composable
fun LandingScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(LoginBg)
            .padding(Dimens.LoginPadding)
    ){
        RegisterComponent(
            modifier = Modifier.padding(
                top = 50.dp
            )
        )
    }

}

@Composable
fun RegisterComponent(modifier: Modifier) {
    Column(modifier) {
        LoginTextField(value = "", onValueChange = {}, hint = "Name")
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing))
        LoginTextField(value = "", onValueChange = {}, hint = "User Name")
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing))
        LoginPasswordTextField(value = "", onValueChange = {}, hint = "Password",false,{})
        Spacer(modifier = Modifier.height(Dimens.FormFieldVerticalSpacing))
        LoginPasswordTextField(value = "", onValueChange = {}, hint = "Confirm Password",false,{})
    }
}