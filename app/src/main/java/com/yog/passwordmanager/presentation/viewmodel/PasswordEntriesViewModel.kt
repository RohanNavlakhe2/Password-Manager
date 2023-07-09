package com.yog.passwordmanager.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yog.passwordmanager.domain.PasswordData
import com.yog.passwordmanager.domain.PasswordManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
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