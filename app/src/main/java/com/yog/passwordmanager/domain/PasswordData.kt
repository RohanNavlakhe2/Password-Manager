package com.yog.passwordmanager.domain

data class PasswordData(
    val id:Int? = null,
    val title:String,
    val password:String,
    val isPasswordChecked:Boolean = false
)
