package com.neacoding.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class GhostToolButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun exposesContentDescription() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                GhostToolButton(
                    icon = Icons.Default.Refresh,
                    contentDescription = "Effacer",
                    onClick = {},
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Effacer").assertIsDisplayed()
    }

    @Test
    fun click_invokesCallback() {
        var clicks = 0
        composeTestRule.setContent {
            GuessMyDrawTheme {
                GhostToolButton(
                    icon = Icons.Default.Refresh,
                    contentDescription = "Effacer",
                    onClick = { clicks++ },
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Effacer").performClick()
        assert(clicks == 1)
    }
}
