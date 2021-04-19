package openrgb.effects

import openrgb.Effect
import openrgb.EffectsEnum
import java.awt.Color
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.file.FileSystems

class KeyboardEffect(override val fps: Int, val startHex: String, val endHex: String) : Effect() {


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

        startBackend()

        originalStartColor = Color.decode(startHex)
        originalEndColor = Color.decode(endHex)

        colorHex = startHex

    }

    private fun startBackend() {
        Thread {
            val file = File("files${FileSystems.getDefault().separator}backend_files${FileSystems.getDefault().separator}keyboard_listener.py")
            Runtime.getRuntime()
                .exec("python ${file.absolutePath}")

            val server = ServerSocket(6969)
            val socket = server.accept()

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

            while (true) {
                Thread.sleep((1000 / fps).toLong())

                if(reader.readLine() == "start") {
                    colorHex = endHex
                }else {
                    colorHex = startHex
                }

                Thread.sleep(50)
            }
        }.start()
    }

}