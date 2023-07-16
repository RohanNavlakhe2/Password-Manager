package com.yog.passwordmanager.presentation.state

data class LoginFieldState(
    var fieldValue:String,
    var error:Boolean = false,
    var errorText:String = "",
    var isPasswordVisible:Boolean = false
)
