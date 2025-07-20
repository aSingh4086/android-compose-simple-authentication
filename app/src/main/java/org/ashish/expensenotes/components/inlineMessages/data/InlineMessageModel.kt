package org.ashish.expensenotes.components.inlineMessages.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class InlineMessageModel(
    val severity: String,
    val message: String,
)

data class InlineMessageItemModel(
    val content: @Composable () -> Unit,
    val severity: String,
)

data class UserMessageList(
    val messages: List<InlineMessageModel> = listOf(),
)


enum class MessageSeverity(val severity: String) {
    ERROR("ERROR"),
    SUCCESS("SUCCESS"),
    WARNING("WARNING"),
    INFO("INFO")
}


object InlineMessageColor {
    val c_inline_messages__information_background_color = DesignToken.ColorToken.color_zircon
    val c_inline_messages__information_border_color = DesignToken.ColorToken.color_titan_white
    val c_inline_messages__information_icon_color = DesignToken.ColorToken.color_cornflower_blue

    val c_inline_messages__error_background_color = DesignToken.ColorToken.color_wisp_pink
    val c_inline_messages__error_border_color = DesignToken.ColorToken.color_azalea
    val c_inline_messages__error_icon_color = DesignToken.ColorToken.color_crimson

    val c_inline_messages__success_background_color = DesignToken.ColorToken.color_aqua_haze
    val c_inline_messages__success_border_color = DesignToken.ColorToken.color_edge_water
    val c_inline_messages__success_icon_color = DesignToken.ColorToken.color_genoa


    val c_inline_messages__warning_background_color = DesignToken.ColorToken.color_linen
    val c_inline_messages__warning_border_color = DesignToken.ColorToken.color_sidecar
    val c_inline_messages__warning_icon_color = DesignToken.ColorToken.color_indochine
}
object DesignToken {
    object ColorToken {
        val color_zircon = Color(0xFFE4E8FF) // Light blue shade
        val color_titan_white = Color(0xFFF5F7FF) // Very light blue
        val color_cornflower_blue = Color(0xFF6495ED) // Cornflower blue
        val color_wisp_pink = Color(0xFFFFE4E1) // Light pink
        val color_azalea = Color(0xFFF7C6D9) // Soft pink
        val color_crimson = Color(0xFFDC143C) // Crimson red
        val color_aqua_haze = Color(0xFFF5FFFA) // Light aqua
        val color_edge_water = Color(0xFFC1E1C1) // Soft green
        val color_genoa = Color(0xFF157A6E) // Dark green
        val color_linen = Color(0xFFFAF0E6) // Linen color
        val color_sidecar = Color(0xFFF4E1C1) // Beige
        val color_indochine = Color(0xFFCD853F) // Brownish orange
    }
}