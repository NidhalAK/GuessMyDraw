package com.neacoding.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Centered secret-word mask chip displaying letter placeholders with generous spacing, e.g.
 * `_ _ _ _ _`. Revealed characters in [revealed] replace the corresponding placeholders.
 */
@Composable
fun SecretWordChip(
    revealed: String,
    length: Int,
    modifier: Modifier = Modifier,
) {
    val display = buildString {
        for (i in 0 until length) {
            append(if (i < revealed.length && revealed[i] != ' ') revealed[i] else '_')
            if (i != length - 1) append(' ')
        }
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = display,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Secret word chip", showBackground = true, backgroundColor = 0xFF0F0B3C)
@Composable
private fun SecretWordChipPreview() {
    GuessMyDrawTheme {
        SecretWordChip(
            revealed = "C",
            length = 6,
            modifier = Modifier.padding(16.dp),
        )
    }
}
