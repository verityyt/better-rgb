package openrgb.backends

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.nio.file.FileSystems

object BackendManager {

    var keyboardListenerSignal: String = ""

    fun startUpAll() {
        startupKeyboardListener()
    }

    private fun startupKeyboardListener() {
        Thread {
            try {
                val file =
                    File("files${FileSystems.getDefault().separator}backend_files${FileSystems.getDefault().separator}keyboard_listener.py")
                Runtime.getRuntime()
                    .exec("python ${file.absolutePath}")
            } catch (e: Exception) {
            }


            val server = ServerSocket(6969)
            val socket = server.accept()

            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

            while (true) {
                keyboardListenerSignal = reader.readLine()
            }
        }.start()
    }

}