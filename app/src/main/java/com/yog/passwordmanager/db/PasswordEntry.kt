package com.yog.passwordmanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yog.passwordmanager.domain.PasswordData

@Entity
data class PasswordEntry(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val title:String,
    val password:String
)

fun PasswordEntry.toPasswordData() = PasswordData(
    id = id,
    title = title,
    password = password,
)
