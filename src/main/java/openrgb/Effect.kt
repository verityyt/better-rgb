package openrgb

import java.awt.Color

open class Effect(val name: String) {

    private var speed = 60
    var colorHex = "#FFFFFF"

    open var animation = false
    open val fps = 0

    open var thread = Thread {}

    open fun start() = thread.start()

    open fun join() = thread.join()

    open fun stop() = thread.stop()

}