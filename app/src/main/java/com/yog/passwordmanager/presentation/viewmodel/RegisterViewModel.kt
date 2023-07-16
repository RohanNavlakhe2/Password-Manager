package com.yog.passwordmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import com.yog.passwordmanager.presentation.state.LoginFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val passwordManagerRepository: PasswordManagerRepository,
    private val dispatcher: CoroutineDispatcher
):ViewModel() {

    private val _userNameStateFlow = MutableStateFlow(LoginFieldState(""))
    val userNameStateFlow:StateFlow<LoginFieldState> = _userNameStateFlow

    private val _passwordStateFlow = MutableStateFlow(LoginFieldState(""))
    val passwordStateFlow:StateFlow<LoginFieldState> = _passwordStateFlow

    private val _confirmPasswordStateFlow = MutableStateFlow(LoginFieldState(""))
    val confirmPasswordStateFlow:StateFlow<LoginFieldState> = _confirmPasswordStateFlow

    fun onEvent(registerEvent: RegisterEvent){
        when(registerEvent){
            is RegisterEvent.UsernameChanged -> {
                _userNameStateFlow.value = userNameStateFlow.value.copy(
                    fieldValue = registerEvent.username
                )
            }
            is RegisterEvent.PasswordChanged -> {
                _passwordStateFlow.value = passwordStateFlow.value.copy(
                    fieldValue = registerEvent.password
                )
            }
            is RegisterEvent.ConfirmPasswordChanged -> {
                _confirmPasswordStateFlow.value = confirmPasswordStateFlow.value.copy(
                    fieldValue = registerEvent.confirmPassword
                )
            }

            is RegisterEvent.TogglePasswordVisibility -> {
                _passwordStateFlow.value = passwordStateFlow.value.copy(
                    isPasswordVisible = !passwordStateFlow.value.isPasswordVisible
                )
            }

            is RegisterEvent.ToggleConfirmPasswordVisibility -> {
               _confirmPasswordStateFlow.value = confirmPasswordStateFlow.value.copy(
                    isPasswordVisible = !confirmPasswordStateFlow.value.isPasswordVisible
                )
            }
        }
    }

    sealed interface RegisterEvent{
        data class UsernameChanged(val username:String):RegisterEvent
        data class PasswordChanged(val password:String):RegisterEvent
        data class ConfirmPasswordChanged(val confirmPassword:String):RegisterEvent
        object TogglePasswordVisibility:RegisterEvent
        object ToggleConfirmPasswordVisibility:RegisterEvent
    }


}