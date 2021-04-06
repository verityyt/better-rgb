import userinterface.CustomFont
import userinterface.WindowHandler
import utils.Logger

import openrgb.OpenRGBManager

object BetterRGB {

    var version = "0.1.2"
    var buildDate = "/"

    @JvmStatic
    fun main(args: Array<String>) {
        Logger.debug("Starting up BetterRGB...")

        OpenRGBManager.connect()

        CustomFont.registerFonts()
        WindowHandler.openWindow()

        Logger.info("Started up BetterRGB!")
    }

}