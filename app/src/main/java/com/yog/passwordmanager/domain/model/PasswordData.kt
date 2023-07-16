package com.yog.passwordmanager.domain.model

data class PasswordData(
    val id:Int? = null,
    val title:String,
    val password:String,
    val isPasswordChecked:Boolean = false
)
