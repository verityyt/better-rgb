import userinterface.CustomFont
import userinterface.WindowHandler
import utils.Logger

import openrgb.OpenRGBManager
import userinterface.CustomLabels
import userinterface.SystemTray

object BetterRGB {

    var version = "1.0"
    var buildDate = "4/9/2021"

    @JvmStatic
    fun main(args: Array<String>) {
        Logger.debug("Starting up BetterRGB...")

        OpenRGBManager.connect()

        SystemTray.setTrayEntry()
        CustomLabels.createFile()

        CustomFont.registerFonts()
        WindowHandler.openWindow()

        Logger.info("Started up BetterRGB!")
    }

}