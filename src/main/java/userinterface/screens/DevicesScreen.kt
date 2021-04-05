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

class DevicesScreen : Screen() {

    private var buttonHovered = false

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        if (OpenRGBManager.connected) {

        } else {

            g.color = ColorPalette.foreground
            g.font = CustomFont.regular?.deriveFont(36f)
            g.drawString("Could not connect to OpenRGB", 375, 305)

            g.font = CustomFont.regular?.deriveFont(18f)
            g.drawString("Please start SDK Server in OpenRGB, and try again.", 425, 340)

            /* Draw 'try again' button */

            g2.color = ColorPalette.foreground
            g2.setOpacity(if(buttonHovered) {
                0.4f
            }else {
                0.2f
            })
            g2.fillRect(550, 370, 140, 35)
            g2.resetOpacity()

            g.font = CustomFont.regular?.deriveFont(18f)
            g.drawString("TRY AGAIN", 576, 395)

        }

    }

    override fun mouseClicked(x: Int, y: Int) {
        if(x in 549..689 && y in 349..404) {
            Thread {
                OpenRGBManager.connect()
            }.start()
        }
    }

    override fun mouseMoved(x: Int, y: Int) {
        buttonHovered = (x in 549..689 && y in 349..404)
    }

}