package com.yog.passwordmanager.presentation.state

import androidx.annotation.StringRes
import com.yog.passwordmanager.R

data class LoginFieldState(
    var fieldValue:String,
    var error:Boolean = false,
    @StringRes var errorTextId:Int = R.string.empty,
    var isPasswordVisible:Boolean = false
)
