package openrgb

enum class EffectsEnum(val needPrimary: Boolean, val needSecondary: Boolean) {

    STATIC(true, false),
    COLOR_GRADIENT(true, true),
    RAINBOW_WAVE(false, false)

}