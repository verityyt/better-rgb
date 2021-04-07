package openrgb

import java.awt.Color

open class Effect {

    /**
     * Current color of the effect
     */
    var colorHex = "#FFFFFF"

    /**
     * Whether the effect is a animation or not *(static)*
     */
    open var animation = false

    /**
     * FPS *(Frames per second)* of the animation
     */
    open val fps = 0

    /**
     * Thread of the effect *(is used to change [colorHex])*
     */
    open var thread = Thread {}

    /**
     * [EffectsEnum] of the effect
     */
    open var enumTpe: EffectsEnum = EffectsEnum.STATIC

    open var originalStartColor: Color? = null

    open var originalEndColor: Color? = null

    open var startColor: Color? = null

    open var endColor: Color? = null

    open fun start() = thread.start()

    open fun join() = thread.join()

}