package com.neacoding.designsystem

import androidx.compose.ui.graphics.Color

/**
 * Brand accent colors from the Stitch "Electric Doodle Arcade" system that don't map
 * cleanly onto Material's [androidx.compose.material3.ColorScheme] roles — mainly the
 * saturated action hues and their darker solid extrusion (drop-shadow) shades used by
 * the chunky 3D neo-pop components.
 */
object ArcadeColors {
    val MintAction = Color(0xFF10B981)
    val MintActionShadow = Color(0xFF047857)
    val OnMintAction = Color(0xFFFFFFFF)

    val Gold = Color(0xFFFBBF24)
    val GoldShadow = Color(0xFFB45309)
    val OnGold = Color(0xFF261A00)

    val Coral = Color(0xFFF97316)
    val Danger = Color(0xFFEF4444)
    val GuesserCyan = Color(0xFF06B6D4)

    /** Deep midnight navy used for floating tier (modals / podiums) solid shadows. */
    val MidnightShadow = Color(0xFF0A0826)
    val ElectricOutline = Color(0xFF6366F1)
}
