package com.yog.passwordmanager.data

import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.db.PasswordManagerDao
import com.yog.passwordmanager.db.toPasswordData
import com.yog.passwordmanager.domain.model.PasswordData
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PasswordManagerRepoImpl(
    private val dao:PasswordManagerDao
): PasswordManagerRepository {

    /*override fun getPasswords(): Flow<List<PasswordEntry>> {
       return dao.getPasswords()
    }*/

    override fun getPasswords(): Flow<MutableList<PasswordData>> = flow {
        dao.getPasswords().collect{

            val passwordDataList:MutableList<PasswordData>  = it.map { passwordEntry ->
                passwordEntry.toPasswordData()
            } as MutableList<PasswordData>

            emit(passwordDataList)
        }
    }

    override suspend fun insertPasswordEntry(passwordEntry: PasswordEntry) {
         dao.insertPasswordEntry(passwordEntry = passwordEntry)
    }

    override suspend fun getPasswordEntryById(id: Int):PasswordEntry? {
         return dao.getPasswordEntryById(id)
    }

    override suspend fun deletePasswordEntry(id:Int) {
         dao.deletePasswordEntry(id)
    }
}