package com.malikali.rxjava.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {


    @get:Rule
    val instantTaskExecutable = InstantTaskExecutorRule()

}