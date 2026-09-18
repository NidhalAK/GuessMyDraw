package com.neacoding.designsystem

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class TimeBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rendersTrack() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                TimeBar(
                    progress = 0.5f,
                    modifier = Modifier
                        .width(200.dp)
                        .testTag("time_bar"),
                )
            }
        }
        composeTestRule.onNodeWithTag("time_bar").assertIsDisplayed()
    }

    @Test
    fun clampsProgress_withoutCrashing() {
        composeTestRule.setContent {
            GuessMyDrawTheme {
                TimeBar(
                    progress = 2f,
                    modifier = Modifier
                        .width(200.dp)
                        .testTag("time_bar"),
                )
            }
        }
        composeTestRule.onNodeWithTag("time_bar").assertIsDisplayed()
    }
}
