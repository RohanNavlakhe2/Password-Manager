package com.yog.passwordmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [PasswordEntry::class],
    version = 1
)
abstract class PasswordManagerDatabase:RoomDatabase() {
    abstract val passwordManagerDao: PasswordManagerDao

    companion object {
        const val DATABASE_NAME = "password_manager_db"
    }
}