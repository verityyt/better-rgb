package userinterface.screens

import openrgb.OpenRGBManager
import resetOpacity
import setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Screen
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver
import java.io.File
import javax.imageio.ImageIO

class DevicesScreen : Screen() {

    private var buttonHovered = false

    private var drawDeviceY = 175

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        if (OpenRGBManager.connected) {

            for (deviceIndex in 0 until OpenRGBManager.reveredDevices.size) {
                var deviceName = OpenRGBManager.reveredDevices[deviceIndex]!!

                if (deviceName.length > 25) {
                    deviceName = deviceName.substring(0, 23) + "..."
                    deviceName = deviceName.substring(0, 23) + "..."
                }

                g.color = ColorPalette.foreground
                g.font = CustomFont.regular?.deriveFont(24f)

                g.drawString(
                    deviceName, if (deviceIndex <= 8) {
                        225
                    } else {
                        660
                    }, drawDeviceY
                )

                g.fillRoundRect(
                    if (deviceIndex <= 8) {
                        560
                    } else {
                        990
                    }, drawDeviceY - 25, 35, 35, 10, 10
                )

                g.drawImage(
                    ImageIO.read(File("files\\images\\devices\\configure.png")), if (deviceIndex <= 8) {
                        566
                    } else {
                        996
                    }, drawDeviceY - 19, 24, 24, observer
                )

                if (deviceIndex == 8) {
                    drawDeviceY = 175
                } else {
                    drawDeviceY += 50
                }
            }

            drawDeviceY = 175

        } else {

            g.color = ColorPalette.foreground
            g.font = CustomFont.regular?.deriveFont(36f)
            g.drawString("Could not connect to OpenRGB", 375, 305)

            g.font = CustomFont.regular?.deriveFont(18f)
            g.drawString("Please start SDK Server in OpenRGB, and try again.", 425, 340)

            /* Draw 'try again' button */

            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (buttonHovered) {
                    0.4f
                } else {
                    0.2f
                }
            )
            g2.fillRect(550, 370, 140, 35)
            g2.resetOpacity()

            g.font = CustomFont.regular?.deriveFont(18f)
            g.drawString("TRY AGAIN", 576, 395)

        }

    }

    override fun mouseClicked(x: Int, y: Int) {
        if (x in 549..689 && y in 349..404) {
            Thread {
                OpenRGBManager.connect()
            }.start()
        }
    }

    override fun mouseMoved(x: Int, y: Int) {
        buttonHovered = (x in 549..689 && y in 349..404)
    }

}