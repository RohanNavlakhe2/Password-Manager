package com.yog.passwordmanager.presentation.state

data class PasswordFieldState(
    val hint: String,
    val text: String,
    val isPasswordVisible:Boolean = false
)
