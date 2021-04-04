package userinterface.screens

import userinterface.ColorPalette
import userinterface.Screen
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class HelpScreen : Screen() {

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {
        g.color = ColorPalette.foreground
        g.font = g.font.deriveFont(42f)
        g.drawString("Hello there!", 100, 100)
    }

    override fun mouseClicked(x: Int, y: Int) {}

    override fun mouseMoved(x: Int, y: Int) {}

}