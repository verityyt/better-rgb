package userinterface

import utils.Logger
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import java.io.File
import java.nio.file.FileSystems
import javax.imageio.ImageIO
import kotlin.system.exitProcess

object SystemTray {

    /**
     * [SystemTray] of current operating system
     */
    private var systemTray: SystemTray? = null

    /**
     * [TrayIcon] in [SystemTray][systemTray] of BetterRGBs
     */
    private var trayIcon: TrayIcon? = null

    /**
     * Function to add the [TrayIcon][trayIcon] to the [SystemTray][systemTray]
     */
    fun setTrayEntry() {
        if (SystemTray.isSupported()) {
            Logger.info("SystemTray is supported!")

            systemTray = SystemTray.getSystemTray()

            val image =
                ImageIO.read(File("files${FileSystems.getDefault().separator}images${FileSystems.getDefault().separator}logo${FileSystems.getDefault().separator}logo_favicon.png"))
            trayIcon = TrayIcon(image, "BetterRGB")
            trayIcon!!.isImageAutoSize = true

            val popup = PopupMenu()
            val showItem = MenuItem("Show/Hide")
            val exitItem = MenuItem("Exit")

            showItem.addActionListener { clickShow() }
            exitItem.addActionListener { clickExit() }

            popup.add(showItem)
            popup.add(exitItem)

            trayIcon!!.popupMenu = popup
            systemTray!!.add(trayIcon)

        } else {
            Logger.warn("SystemTray is not supported")
        }
    }

    /**
     * Function which is called when user clicks at **Show/Hide** in [TrayIcon][trayIcon]s [PopupMenu]
     */
    private fun clickShow() {
        if (WindowHandler.frame.isVisible) {
            WindowHandler.frame.dispose()
        } else {
            WindowHandler.frame.isVisible = true
        }
    }

    /**
     * Function which is called when user clicks at **Exit** in [TrayIcon][trayIcon]s [PopupMenu]
     */
    private fun clickExit() {
        systemTray!!.remove(trayIcon)
        exitProcess(-1)
    }

}