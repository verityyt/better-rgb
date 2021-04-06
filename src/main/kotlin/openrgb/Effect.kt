package openrgb

open class Effect {

    var colorHex = "#FFFFFF"

    open var animation = false
    open val fps = 0

    open var thread = Thread {}

    open fun start() = thread.start()

    open fun join() = thread.join()

}