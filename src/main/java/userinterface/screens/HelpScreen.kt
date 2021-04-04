package userinterface.screens

import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Screen
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class HelpScreen : Screen() {

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        /* Software Information */

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(22f)
        g.drawString("Software Information:", 180, 70)

        g.font = CustomFont.regular?.deriveFont(18f)
        g.drawString("Version:", 205, 108)
        g.drawString("v${BetterRGB.version}", 375, 108)

        g.drawString("Build date:", 205, 135)
        g.drawString(BetterRGB.buildDate, 375, 135)

        /* Credits */

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(22f)
        g.drawString("Credits:", 745, 70)

        g.font = CustomFont.regular?.deriveFont(18f)
        g.drawString("Designer:", 770, 108)
        g.drawString("verity", 940, 108)

        g.drawString("Head Developer:", 770, 135)
        g.drawString("verity", 940, 135)

    }

    override fun mouseClicked(x: Int, y: Int) {}

    override fun mouseMoved(x: Int, y: Int) {}

}