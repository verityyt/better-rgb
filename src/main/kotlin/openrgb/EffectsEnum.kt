package openrgb

enum class EffectsEnum(val displayName: String, val needPrimary: Boolean, val needSecondary: Boolean) {

    STATIC("Static",true, false),
    COLOR_GRADIENT("Color Gradient",true, true),
    RAINBOW_WAVE("Rainbow Wave",false, false)

}