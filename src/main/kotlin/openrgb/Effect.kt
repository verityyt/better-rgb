package openrgb

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

    open fun start() = thread.start()

    open fun join() = thread.join()

}