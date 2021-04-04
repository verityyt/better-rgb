package userinterface

import utils.Logger
import java.awt.*
import java.io.File

object CustomFont {

    var bold: Font? = null
    var regular: Font? = null
    var light: Font? = null

    fun registerFonts() {
        registerRegular()
        registerLight()
        registerBold()

        Logger.info("Registered Fonts!")
    }


    fun registerRegular() {
        regular =
            Font.createFont(Font.TRUETYPE_FONT, File("files/fonts/Product-Sans-Regular.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(regular)
    }

    fun registerLight() {
        light =
            Font.createFont(Font.TRUETYPE_FONT, File("files/fonts/Product-Sans-Light.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(light)
    }

    fun registerBold() {
        bold =
            Font.createFont(Font.TRUETYPE_FONT, File("files/fonts/Product-Sans-Bold.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(bold)
    }

    fun drawCentredString(graphics: Graphics, rect: Rectangle, text: String, color: Color, font: Font) {
        val metrics: FontMetrics = graphics.getFontMetrics(font)
        val x: Int = rect.x + (rect.width - metrics.stringWidth(text)) / 2
        val y: Int = rect.y + (rect.height - metrics.height) / 2 + metrics.ascent
        graphics.font = font
        graphics.color = color
        graphics.drawString(text, x, y)
    }

}