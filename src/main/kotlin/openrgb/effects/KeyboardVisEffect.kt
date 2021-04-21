package openrgb.effects

import openrgb.Effect
import openrgb.EffectsEnum
import openrgb.backends.BackendManager
import java.awt.Color
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.file.FileSystems

class KeyboardVisEffect(override val fps: Int, val startHex: String, val endHex: String) : Effect() {


    /**
     * Start color of current blend
     */
    override var startColor = Color.decode(startHex)

    /**
     * End color of current blend
     */
    override var endColor = Color.decode(endHex)

    /**
     * Original start color *(does not change until effect is delete)*
     */
    override var originalStartColor = Color.white

    /**
     * Original end color *(does not change until effect is delete)*
     */
    override var originalEndColor = Color.white

    /**
     * Whether the effect is a animation or not *(static)*
     */
    override var animation = true

    /**
     * [EffectsEnum] of the effect
     */
    override var enumType: EffectsEnum = EffectsEnum.KEYBOARD_VIS

    init {
        colorHex = String.format("#%02x%02x%02x", startColor.red, startColor.green, startColor.blue)
    }

    override var thread = Thread {

        originalStartColor = Color.decode(startHex)
        originalEndColor = Color.decode(endHex)

        var old = ""

        var time = 0.0
        var holding = true

        Thread {
            while (true) {
                Thread.sleep(150)
                if (holding) {
                    time++
                }
            }
        }.start()

        while (true) {
            val input = BackendManager.keyboardListenerSignal

            if (old != input) {
                if (input == "start") {
                    holding = true
                    time = 0.0

                    colorHex = endHex
                } else {
                    holding = false

                    if (time < 2.0) {
                        Thread.sleep(300)
                    }

                    colorHex = startHex
                }
            } else {
                print("")
            }

            old = input
        }

    }

}