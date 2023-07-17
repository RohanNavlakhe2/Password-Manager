package com.yog.passwordmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.yog.passwordmanager.R
import com.yog.passwordmanager.domain.repository.PasswordManagerRepository
import com.yog.passwordmanager.presentation.state.LoginFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.regex.Pattern
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

    private fun validate(username: String,password: String,confirmPassword: String){
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

        val passwordPattern = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$"

         if(!Pattern.matches(passwordPattern,password)){
             _passwordStateFlow.value = passwordStateFlow.value.copy(
                 error = true,
                 errorTextId = R.string.password_requirement
             )
         }else{
             _passwordStateFlow.value = passwordStateFlow.value.copy(
                 error = false,
             )
         }

        if(confirmPassword != password){
            _confirmPasswordStateFlow.value = confirmPasswordStateFlow.value.copy(
                error = true,
                errorTextId = R.string.confirm_password_requirement
            )
        }else{
            _confirmPasswordStateFlow.value = confirmPasswordStateFlow.value.copy(
                error = false,
            )
        }

    }

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

            is RegisterEvent.Validate -> {
                validate(registerEvent.username,registerEvent.password,registerEvent.confirmPassword)
            }
        }
    }

    sealed interface RegisterEvent{
        data class UsernameChanged(val username:String):RegisterEvent
        data class PasswordChanged(val password:String):RegisterEvent
        data class ConfirmPasswordChanged(val confirmPassword:String):RegisterEvent
        object TogglePasswordVisibility:RegisterEvent
        object ToggleConfirmPasswordVisibility:RegisterEvent
        data class Validate(val username: String,val password: String,val confirmPassword: String):RegisterEvent
    }


}