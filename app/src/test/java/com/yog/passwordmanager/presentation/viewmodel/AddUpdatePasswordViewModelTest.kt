package com.yog.passwordmanager.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.yog.passwordmanager.data.PasswordManagerFakeRepository
import com.yog.passwordmanager.db.PasswordEntry
import com.yog.passwordmanager.rule.MainDispatcherRule2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddUpdatePasswordViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule2()

    private lateinit var viewModel:AddUpdatePasswordViewModel

    @Before
    fun setup(){
        viewModel = AddUpdatePasswordViewModel(
            PasswordManagerFakeRepository(),
            SavedStateHandle(),
            StandardTestDispatcher()
        )
    }

    @Test
    fun getPasswordEntry_PassId1_TitleAndPasswordShouldMatchWithFakeRepository() = runTest{
        viewModel.getPasswordEntry(1)
        advanceUntilIdle()
        Assert.assertEquals(viewModel.titleState.value.text,"Google")
        Assert.assertEquals(viewModel.passwordState.value.text,"Google@12")
    }

    @Test
    fun insertPasswordEntry_getPassword_SizeShouldBe4AndShouldExist() = runTest{

        viewModel.insertPasswordEntry(
            PasswordEntry(
                4,"Github","Git@12"
            )
        )

        advanceUntilIdle()

        viewModel.getPasswordEntry(4)

        advanceUntilIdle()
        Assert.assertEquals(viewModel.titleState.value.text,"Github")
        Assert.assertEquals(viewModel.passwordState.value.text,"Git@12")
    }




}