package com.neacoding.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Tactile 3D action pill from the Stitch "Electric Doodle Arcade" system. Fully rounded,
 * min-height 52dp, styled with a solid bottom extrusion in [shadowColor]. Tapping collapses
 * the extrusion and translates the pill down for instant physical feedback.
 */
@Composable
fun ArcadePillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = ArcadeColors.MintAction,
    contentColor: Color = ArcadeColors.OnMintAction,
    shadowColor: Color = ArcadeColors.MintActionShadow,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    val (interactionSource, pressed) = rememberPressState()

    Box(
        modifier = modifier
            .heightIn(min = 52.dp)
            .chunkyExtrusion(shadowColor = shadowColor, pressed = pressed)
            .clip(CircleShape)
            .background(if (enabled) containerColor else MaterialTheme.colorScheme.surfaceVariant)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (enabled) contentColor else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = if (enabled) contentColor else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/** Primary CTA ("Valider", "Prêt !") — mint victory pill. */
@Composable
fun MintActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
) = ArcadePillButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    containerColor = ArcadeColors.MintAction,
    contentColor = ArcadeColors.OnMintAction,
    shadowColor = ArcadeColors.MintActionShadow,
    enabled = enabled,
    leadingIcon = leadingIcon,
)

/** Special/booster actions ("Utiliser un indice", "Rejouer") — gold pill. */
@Composable
fun GoldBoosterButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
) = ArcadePillButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    containerColor = ArcadeColors.Gold,
    contentColor = ArcadeColors.OnGold,
    shadowColor = ArcadeColors.GoldShadow,
    enabled = enabled,
    leadingIcon = leadingIcon,
)

@Preview(name = "Pill buttons", showBackground = true, backgroundColor = 0xFF0F0B3C)
@Composable
private fun ArcadePillButtonPreview() {
    GuessMyDrawTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            MintActionButton(text = "Valider", onClick = {})
            GoldBoosterButton(text = "Indice", onClick = {})
            MintActionButton(text = "Désactivé", onClick = {}, enabled = false)
        }
    }
}
