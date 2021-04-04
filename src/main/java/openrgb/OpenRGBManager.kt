package openrgb

import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import utils.Logger
import java.lang.Exception
import java.lang.NullPointerException
import java.net.ConnectException

object OpenRGBManager {

    lateinit var client: OpenRGBClient
    var connected: Boolean = false

    fun connect() {
        Logger.debug("Connecting to OpenRGB...")

        try {
            client = OpenRGBClient("localhost", 6742, "BetterRGB | v${BetterRGB.version} - ${BetterRGB.buildDate}")

            client.connect()

            Logger.info("Successfully connected to OpenRGB!")
        } catch (e: ConnectException) {
            connected = false
            Logger.warn("Failed to connect to OpenRGB!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}