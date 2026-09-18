package com.neacoding.designsystem

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ArcadePillButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysLabel() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                MintActionButton(text = "Valider", onClick = {})
            }
        }
        composeTestRule.onNodeWithText("Valider").assertIsDisplayed()
    }

    @Test
    fun click_invokesCallback_whenEnabled() {
        var clicks = 0
        composeTestRule.setContent {
            GuessMyDrawTheme {
                GoldBoosterButton(text = "Indice", onClick = { clicks++ })
            }
        }
        composeTestRule.onNodeWithText("Indice").performClick()
        assert(clicks == 1)
    }

    @Test
    fun click_isIgnored_whenDisabled() {
        var clicks = 0
        composeTestRule.setContent {
            GuessMyDrawTheme {
                MintActionButton(text = "Valider", onClick = { clicks++ }, enabled = false)
            }
        }
        composeTestRule.onNodeWithText("Valider").performClick()
        assert(clicks == 0)
    }
}
