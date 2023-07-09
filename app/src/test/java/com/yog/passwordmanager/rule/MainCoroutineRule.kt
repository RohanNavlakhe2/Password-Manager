package com.yog.passwordmanager.rule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
    class MainCoroutineRule(
        //private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
        private val dispatcher: TestDispatcher =  UnconfinedTestDispatcher()
    ) : TestWatcher() {
        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(dispatcher)
        }
    
        override fun finished(description: Description?) {
            Dispatchers.resetMain()
            super.finished(description)
            //cleanupTestCoroutines()

        }
    }