package com.neacoding.designsystem

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class StatusPillTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysUppercasedLabel() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                StatusPill(text = "Dessinateur")
            }
        }
        composeTestRule.onNodeWithText("DESSINATEUR").assertIsDisplayed()
    }
}
