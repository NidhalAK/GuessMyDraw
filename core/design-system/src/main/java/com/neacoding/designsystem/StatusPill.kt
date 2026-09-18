package com.neacoding.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Mini capsule status indicator (e.g. "DESSINATEUR", "A TROUVÉ !", "CHAUFFEUR") with a bold
 * uppercase label and optional leading micro-icon, tinted by role. From the Stitch chips spec.
 */
@Composable
fun StatusPill(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = ArcadeColors.Gold,
    contentColor: Color = ArcadeColors.OnGold,
    leadingIcon: ImageVector? = null,
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(containerColor)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(14.dp),
            )
        }
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
        )
    }
}

@Preview(name = "Status pills", showBackground = true, backgroundColor = 0xFF0F0B3C)
@Composable
private fun StatusPillPreview() {
    GuessMyDrawTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StatusPill(text = "Dessinateur")
            StatusPill(
                text = "A trouvé !",
                containerColor = ArcadeColors.MintAction,
                contentColor = ArcadeColors.OnMintAction,
            )
            StatusPill(
                text = "Chauffeur",
                containerColor = ArcadeColors.GuesserCyan,
                contentColor = Color.White,
            )
        }
    }
}
