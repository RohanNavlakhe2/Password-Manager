package com.yog.passwordmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.yog.passwordmanager.R
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import com.yog.passwordmanager.presentation.state.LoginFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val passwordManagerRepository: PasswordManagerRepository,
    private val dispatcher: CoroutineDispatcher
):ViewModel() {

    private val _userNameStateFlow = MutableStateFlow(LoginFieldState(""))
    val userNameStateFlow:StateFlow<LoginFieldState> = _userNameStateFlow

    private val _passwordStateFlow = MutableStateFlow(LoginFieldState(""))
    val passwordStateFlow:StateFlow<LoginFieldState> = _passwordStateFlow

    private fun validate(username: String,password: String){

        if(username.trim().isEmpty()){
            _userNameStateFlow.value = userNameStateFlow.value.copy(
                error = true,
                errorTextId = R.string.username_requirement
            )
        }else{
            _userNameStateFlow.value = userNameStateFlow.value.copy(
                error = false,
            )
        }

        if(password.trim().isEmpty()){
            _passwordStateFlow.value = passwordStateFlow.value.copy(
                error = true,
                errorTextId = R.string.password_not_empty
            )
        }else{
            _passwordStateFlow.value = passwordStateFlow.value.copy(
                error = false,
            )
        }
    }

    fun onEvent(loginEvent: LoginEvent){
        when(loginEvent){
            is LoginEvent.UsernameChanged -> {
                _userNameStateFlow.value = userNameStateFlow.value.copy(
                    fieldValue = loginEvent.username
                )
            }
            is LoginEvent.PasswordChanged -> {
                _passwordStateFlow.value = passwordStateFlow.value.copy(
                    fieldValue = loginEvent.password
                )
            }

            is LoginEvent.TogglePasswordVisibility -> {
                _passwordStateFlow.value = passwordStateFlow.value.copy(
                    isPasswordVisible = !passwordStateFlow.value.isPasswordVisible
                )
            }

            is LoginEvent.Validate -> {
                validate(loginEvent.username,loginEvent.password)
            }
        }
    }

    sealed interface LoginEvent{
        data class UsernameChanged(val username:String):LoginEvent
        data class PasswordChanged(val password:String):LoginEvent
        object TogglePasswordVisibility:LoginEvent
        data class Validate(val username: String,val password: String):LoginEvent
    }


}