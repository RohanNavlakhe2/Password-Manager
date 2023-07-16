package com.yog.passwordmanager.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import com.yog.passwordmanager.presentation.state.PasswordFieldState
import com.yog.passwordmanager.presentation.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddUpdatePasswordViewModel @Inject constructor(
    private val passwordManagerRepository: PasswordManagerRepository,
    savedStateHandle: SavedStateHandle,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _titleState = mutableStateOf(
        TextFieldState(hint = "Enter Title Here", text = "")
    )

    val titleState: State<TextFieldState> = _titleState

    private val _passwordState = mutableStateOf(
        PasswordFieldState(hint = "Enter Password Here", text = "")
    )

    val passwordState: State<PasswordFieldState> = _passwordState

    private var currentPasswordEntryId: Int? = null

    init {
        savedStateHandle.get<Int>("passwordEntryId")?.let { passwordEntryId ->
            getPasswordEntry(passwordEntryId)
        }
    }

    fun getPasswordEntry(passwordEntryId: Int) {
        viewModelScope.launch(dispatcher) {
            if (passwordEntryId != -1) {
                passwordManagerRepository.getPasswordEntryById(passwordEntryId)
                    ?.let { passwordEntry ->

                        currentPasswordEntryId = passwordEntry.id

                        withContext(Dispatchers.Main){
                            _titleState.value = titleState.value.copy(
                                text = passwordEntry.title
                            )

                            _passwordState.value = passwordState.value.copy(
                                text = passwordEntry.password
                            )
                        }
                    }
            }

        }
    }

    fun insertPasswordEntry(passwordEntry: PasswordEntry) = viewModelScope.launch(dispatcher) {
        passwordManagerRepository.insertPasswordEntry(passwordEntry)
    }

    fun onEvent(event: AddUpdatePasswordEvent) {
        when (event) {

            is AddUpdatePasswordEvent.TitleTextChanged -> {
                _titleState.value = titleState.value.copy(
                    text = event.text
                )
            }

            is AddUpdatePasswordEvent.PasswordTextChanged -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.text
                )
            }

            is AddUpdatePasswordEvent.PasswordToggle -> {
                _passwordState.value = passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }

            is AddUpdatePasswordEvent.InsertPasswordEntry -> {
                insertPasswordEntry(
                    PasswordEntry(
                        title = titleState.value.text,
                        password = passwordState.value.text,
                        id = currentPasswordEntryId
                    )
                )
            }
        }
    }

    sealed class AddUpdatePasswordEvent {
        data class TitleTextChanged(val text: String) : AddUpdatePasswordEvent()
        data class PasswordTextChanged(val text: String) : AddUpdatePasswordEvent()
        object PasswordToggle : AddUpdatePasswordEvent()
        object InsertPasswordEntry : AddUpdatePasswordEvent()
    }
}