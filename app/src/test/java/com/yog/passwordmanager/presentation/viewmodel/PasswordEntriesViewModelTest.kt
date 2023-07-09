package com.yog.passwordmanager.presentation.viewmodel
//Unit Test Method name convention
//https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html

import com.yog.passwordmanager.data.PasswordManagerFakeRepository
import com.yog.passwordmanager.rule.MainDispatcherRule2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PasswordEntriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule2()

    private lateinit var viewModel: PasswordEntriesViewModel

    @Before
    fun setup() {
        viewModel = PasswordEntriesViewModel(PasswordManagerFakeRepository(),
            StandardTestDispatcher()
        )
    }

    @Test
    fun checkUncheck_SetFirstEntryChecked_ShouldBeTrue() = runTest {
        viewModel.updateCheckedPassword(0, true)
        Assert.assertTrue(viewModel.passwordDataState[0].isPasswordChecked)
    }

    @Test
    fun checkUncheck_SetSecondEntryCheckedThenUnchecked_ShouldBeFalse() = runTest {
        viewModel.updateCheckedPassword(1, true)
        viewModel.updateCheckedPassword(1, false)
        Assert.assertFalse(viewModel.passwordDataState[1].isPasswordChecked)
    }

    @Test
    fun uncheckAll_ShouldBeFalse() = runTest {
        viewModel.unCheckAll()
        viewModel.passwordDataState.forEach {
            Assert.assertFalse(it.isPasswordChecked)
        }
    }

    @Test
    fun deleteSelectedEntries_Select2ndEntry_SizeShouldBe2() = runTest {
        viewModel.updateCheckedPassword(1, true)
        viewModel.deleteSelectedPasswordEntries()
        advanceUntilIdle()
        viewModel.getPasswords()
        advanceUntilIdle()
        Assert.assertEquals(2, viewModel.passwordDataState.size)
    }

    @Test
    fun deleteSelectedEntries_Select2ndEntryAndDelete_FacebookShouldNotBeThere() = runTest {
        viewModel.updateCheckedPassword(1, true)
        viewModel.deleteSelectedPasswordEntries()
        advanceUntilIdle()
        viewModel.getPasswords()
        advanceUntilIdle()

        val isFacebookExists = viewModel.passwordDataState.find {
            it.title == "Facebook"
        }

        Assert.assertNull(isFacebookExists)
    }
}