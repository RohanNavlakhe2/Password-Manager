package com.yog.passwordmanager.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yog.passwordmanager.domain.model.PasswordData
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordEntriesViewModel @Inject constructor(
    private val passwordManagerRepository: PasswordManagerRepository,
    private val dispatcher:CoroutineDispatcher
) : ViewModel() {

    private val _passwordDataState = mutableStateListOf<PasswordData>()
    val passwordDataState: List<PasswordData> = _passwordDataState

    init {
        getPasswords()
    }

    fun getPasswords() {
        viewModelScope.launch(dispatcher) {
            passwordManagerRepository.getPasswords().collect{ passwordEntries ->
                _passwordDataState.clear()
                _passwordDataState.addAll(passwordEntries)
            }
        }
        /*passwordManagerRepository.getPasswords().onEach { passwordEntries ->
            _passwordDataState.clear()
            _passwordDataState.addAll(passwordEntries)
        }.launchIn(viewModelScope)*/
    }

    fun updateCheckedPassword(position: Int, isChecked: Boolean) {
        _passwordDataState[position] = _passwordDataState[position].copy(
            isPasswordChecked = isChecked
        )
    }

    fun unCheckAll() {
        for (i in _passwordDataState.indices) {
            _passwordDataState[i] = _passwordDataState[i].copy(
                isPasswordChecked = false
            )
        }
    }

    fun deleteSelectedPasswordEntries() {
        for (passwordEntry in _passwordDataState) {
            if (passwordEntry.isPasswordChecked) {
                viewModelScope.launch(dispatcher) {
                    passwordEntry.id?.let {
                        passwordManagerRepository.deletePasswordEntry(passwordEntry.id)
                    }
                }
            }
        }
    }
}