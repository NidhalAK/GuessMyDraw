package com.neacoding.designsystem

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Applies the Stitch "chunky 3D extrusion" treatment: a crisp, solid (no-blur) offset
 * shadow rendered in [shadowColor] directly under the element. When [pressed] is true the
 * content translates down by the extrusion depth and the shadow collapses, delivering the
 * tactile neo-pop press feedback described in the design system.
 *
 * Reads the pressed state through a lambda so the animation is applied in the draw/layout
 * phase without triggering recomposition of the caller.
 */
fun Modifier.chunkyExtrusion(
    shadowColor: Color,
    pressed: () -> Boolean,
    cornerRadius: Dp = 9999.dp,
    depth: Dp = 4.dp,
): Modifier = this
    .drawBehind {
        val collapsed = pressed()
        val currentDepth = if (collapsed) 0f else depth.toPx()
        if (currentDepth > 0f) {
            val radiusPx = minOf(cornerRadius.toPx(), size.minDimension / 2f)
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(0f, currentDepth),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(radiusPx, radiusPx),
            )
        }
    }
    .graphicsLayer {
        translationY = if (pressed()) depth.toPx() else 0f
    }

/**
 * Convenience composable state that tracks press interactions and exposes both the
 * [MutableInteractionSource] to hand to a clickable/Button and the current pressed flag.
 */
@Composable
fun rememberPressState(): Pair<MutableInteractionSource, () -> Boolean> {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    return interactionSource to { pressed }
}
