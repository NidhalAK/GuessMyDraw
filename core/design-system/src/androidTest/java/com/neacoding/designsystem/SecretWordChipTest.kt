package com.neacoding.designsystem

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class SecretWordChipTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rendersMaskWithRevealedLetters() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                SecretWordChip(revealed = "C", length = 6)
            }
        }
        composeTestRule.onNodeWithText("C _ _ _ _ _").assertIsDisplayed()
    }

    @Test
    fun rendersFullMask_whenNothingRevealed() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                SecretWordChip(revealed = "", length = 4)
            }
        }
        composeTestRule.onNodeWithText("_ _ _ _").assertIsDisplayed()
    }
}
