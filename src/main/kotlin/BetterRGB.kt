import userinterface.CustomFont
import userinterface.WindowHandler
import utils.Logger

import openrgb.OpenRGBManager

object BetterRGB {

    var version = "1.0-BETA"
    var buildDate = "4/8/2021"

    @JvmStatic
    fun main(args: Array<String>) {
        Logger.debug("Starting up BetterRGB...")

        OpenRGBManager.connect()

        CustomFont.registerFonts()
        WindowHandler.openWindow()

        Logger.info("Started up BetterRGB!")
    }

}