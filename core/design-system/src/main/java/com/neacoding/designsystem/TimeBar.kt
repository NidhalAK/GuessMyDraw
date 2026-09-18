package com.neacoding.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Juicy round-timer bar from the Stitch system: a thick pill progress track that shifts from
 * mint → gold → coral as time runs out, thickening under the 15% urgency threshold.
 * [progress] is the remaining fraction in `0f..1f`.
 */
@Composable
fun TimeBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val clamped = progress.coerceIn(0f, 1f)
    val barColor = when {
        clamped <= 0.15f -> ArcadeColors.Danger
        clamped <= 0.5f -> ArcadeColors.Gold
        else -> ArcadeColors.MintAction
    }
    val thickness = if (clamped <= 0.15f) 16.dp else 12.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(clamped)
                .height(thickness)
                .clip(CircleShape)
                .background(barColor),
        )
    }
}

@Preview(name = "Time bar", showBackground = true, backgroundColor = 0xFF0F0B3C, widthDp = 240)
@Composable
private fun TimeBarPreview() {
    GuessMyDrawTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TimeBar(progress = 0.7f)
        }
    }
}

@Preview(name = "Time bar urgent", showBackground = true, backgroundColor = 0xFF0F0B3C, widthDp = 240)
@Composable
private fun TimeBarUrgentPreview() {
    GuessMyDrawTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            TimeBar(progress = 0.1f)
        }
    }
}
