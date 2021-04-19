package openrgb

enum class EffectsEnum(
    val displayName: String,
    val needPrimary: Boolean,
    val needSecondary: Boolean,
    val needSpeed: Boolean
) {

    STATIC("Static", true, false, false),
    COLOR_GRADIENT("Color Gradient", true, true, true),
    RAINBOW_WAVE("Rainbow Wave", false, false, true),
    BREATHING("Breathing", true, false, true),
    FLASHING("Flashing", true, true, true),
    SCREEN_COLOR("Screen Color", false, false, true),
    KEYBOARD_VIS("Keyboard", true, true, true)

}