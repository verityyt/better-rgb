package userinterface.screens

import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Screen
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class HelpScreen : Screen() {

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        // Software Information

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(22f)
        g.drawString("Software Information:", 180, 70)

        g.font = CustomFont.regular?.deriveFont(18f)
        g.drawString("Version:", 205, 108)
        g.drawString("v${BetterRGB.version}", 375, 108)

        g.drawString("Build date:", 205, 155)
        g.drawString(BetterRGB.buildDate, 375, 155)

        g.drawString("Operating System:", 205, 205)
        g.drawString(System.getProperty("os.name"), 375, 205)

        // Credits

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(22f)
        g.drawString("Credits:", 745, 70)

        g.font = CustomFont.regular?.deriveFont(18f)
        g.drawString("Design:", 770, 108)
        g.drawString("verity", 940, 108)

        g.drawString("Head Developer:", 770, 155)
        g.drawString("verity", 940, 155)

    }

    override fun mouseClicked(x: Int, y: Int) {}

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

}