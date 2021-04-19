import userinterface.CustomFont
import userinterface.WindowHandler
import utils.Logger

import openrgb.OpenRGBManager
import openrgb.backends.BackendManager
import userinterface.CustomLabels
import userinterface.SystemTray

object BetterRGB {

    var version = "1.1"
    var buildDate = "4/10/2021"

    @JvmStatic
    fun main(args: Array<String>) {
        Logger.debug("Starting up BetterRGB...")

        OpenRGBManager.connect()
        BackendManager.startUpAll()

        SystemTray.setTrayEntry()
        CustomLabels.createFile()

        CustomFont.registerFonts()
        WindowHandler.openWindow()

        Logger.info("Started up BetterRGB!")
    }

}