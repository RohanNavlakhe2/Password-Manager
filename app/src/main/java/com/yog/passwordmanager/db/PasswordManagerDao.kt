package com.yog.passwordmanager.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PasswordManagerDao {

    @Query("SELECT * FROM PasswordEntry")
    fun getPasswords(): Flow<MutableList<PasswordEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPasswordEntry(passwordEntry: PasswordEntry)

    @Query("SELECT * FROM PasswordEntry WHERE id = :id")
    suspend fun getPasswordEntryById(id:Int):PasswordEntry?

    @Query("DELETE FROM PasswordEntry WHERE id = :id")
    suspend fun deletePasswordEntry(id:Int)

}