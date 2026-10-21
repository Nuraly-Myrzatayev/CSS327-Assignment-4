package com.example.tiptime

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tipcalculator.TipTimeLayout

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipTimeLayout()
        }
    }
}