package com.yog.passwordmanager.data

import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.domain.model.PasswordData
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PasswordManagerFakeRepository : PasswordManagerRepository {

    private val passwordEntries = mutableListOf(
        PasswordEntry(1, "Google", "Google@12"),
        PasswordEntry(2, "Facebook", "fb@12"),
        PasswordEntry(3, "Apple", "apple@12")
    )

    override fun getPasswords(): Flow<MutableList<PasswordData>> {
        val passwordData = passwordEntries.map {
             PasswordData(
                it.id, it.title, it.password
            )
        }
        return flow {
            emit(passwordData.toMutableList())
        }
    }

    override suspend fun insertPasswordEntry(passwordEntry: PasswordEntry) {
        passwordEntries.add(passwordEntry)
    }

    override suspend fun getPasswordEntryById(id: Int): PasswordEntry? {
        val passwordData = passwordEntries.find {
            it.id == id
        }

        val passwordEntry = passwordData?.let {
            PasswordEntry(
                it.id, it.title, it.password
            )
        }

        return passwordEntry
    }

    override suspend fun deletePasswordEntry(id: Int) {
        val password: PasswordEntry? = passwordEntries.find { it.id == id }
        passwordEntries.remove(password)
    }
}