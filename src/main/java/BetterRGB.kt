import userinterface.CustomFont
import userinterface.WindowHandler
import utils.Logger
import java.text.SimpleDateFormat
import java.util.*

import openrgb.OpenRGBManager

object BetterRGB {

    var version = "0.1.2"
    var buildDate = SimpleDateFormat("MM/dd/YYYY").format(Date())

    @JvmStatic
    fun main(args: Array<String>) {
        Logger.debug("Starting up BetterRGB...")

        OpenRGBManager.connect()
        OpenRGBManager.updateDevices()

        CustomFont.registerFonts()
        WindowHandler.openWindow()

        Logger.info("Started up BetterRGB!")
    }

}