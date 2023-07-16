package com.yog.passwordmanager.domain.repository

import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.domain.model.PasswordData
import kotlinx.coroutines.flow.Flow

interface PasswordManagerRepository {

    //fun getPasswords(): Flow<List<PasswordEntry>>
    fun getPasswords(): Flow<MutableList<PasswordData>>

    suspend fun insertPasswordEntry(passwordEntry: PasswordEntry)

    suspend fun getPasswordEntryById(id:Int):PasswordEntry?

    suspend fun deletePasswordEntry(id:Int)
}