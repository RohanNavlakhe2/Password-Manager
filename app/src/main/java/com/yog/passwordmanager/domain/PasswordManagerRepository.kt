package com.yog.passwordmanager.domain

import com.yog.passwordmanager.db.PasswordEntry
import kotlinx.coroutines.flow.Flow

interface PasswordManagerRepository {

    //fun getPasswords(): Flow<List<PasswordEntry>>
    fun getPasswords(): Flow<MutableList<PasswordData>>

    suspend fun insertPasswordEntry(passwordEntry: PasswordEntry)

    suspend fun getPasswordEntryById(id:Int):PasswordEntry?

    suspend fun deletePasswordEntry(id:Int)
}