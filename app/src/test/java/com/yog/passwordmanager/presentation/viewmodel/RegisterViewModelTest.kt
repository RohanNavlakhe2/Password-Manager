package com.yog.passwordmanager.presentation.viewmodel

import com.yog.passwordmanager.data.PasswordManagerFakeRepository
import com.yog.passwordmanager.rule.MainDispatcherRule2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule2()

    private lateinit var viewModel:RegisterViewModel

    @Before
    fun setup(){
        viewModel = RegisterViewModel(
            PasswordManagerFakeRepository(),
            StandardTestDispatcher()
        )
    }

    @Test
    fun validate_emptyUserNameAndPassword_ShouldBeInvalid(){
        viewModel.onEvent(RegisterViewModel.RegisterEvent.Validate("","",""))
        Assert.assertTrue(viewModel.userNameStateFlow.value.error)
        Assert.assertTrue(viewModel.passwordStateFlow.value.error)
    }

    @Test
    fun validatePassword_lessThan8Chars_ShouldBeInvalid(){
        viewModel.onEvent(RegisterViewModel.RegisterEvent.Validate("","a",""))
        Assert.assertTrue(viewModel.passwordStateFlow.value.error)
    }

    @Test
    fun validatePassword_doesNotContainUpperCase_ShouldBeInvalid(){
        viewModel.onEvent(RegisterViewModel.RegisterEvent.Validate("","abcd@1234",""))
        Assert.assertTrue(viewModel.passwordStateFlow.value.error)
    }

    @Test
    fun validatePassword_matchesPasswordRequirement_ShouldBeValid(){
        viewModel.onEvent(RegisterViewModel.RegisterEvent.Validate("","aBcd@1234",""))
        Assert.assertFalse(viewModel.passwordStateFlow.value.error)
    }

    @Test
    fun validateConfirmPassword_notMatchesPassword_ShouldBeInValid(){
        viewModel.onEvent(RegisterViewModel.RegisterEvent.Validate("","aBcd@1234",""))
        Assert.assertTrue(viewModel.confirmPasswordStateFlow.value.error)
    }


}