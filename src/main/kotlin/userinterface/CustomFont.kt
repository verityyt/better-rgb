package userinterface

import utils.Logger
import java.awt.*
import java.io.File
import java.nio.file.FileSystems

object CustomFont {

    var bold: Font? = null
    var regular: Font? = null
    var light: Font? = null

    /**
     * Registers all fonts
     * *calls all register functions*
     * [registerRegular], [registerLight], [registerBold]
     */
    fun registerFonts() {
        Logger.trace("Registering fonts...")

        registerRegular()
        registerLight()
        registerBold()

        Logger.`interface`("Registered Fonts!")
    }

    /**
     * Registers **regular** font
     */
    private fun registerRegular() {
        regular =
            Font.createFont(Font.TRUETYPE_FONT, File("files${FileSystems.getDefault().separator}fonts${FileSystems.getDefault().separator}Product-Sans-Regular.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(regular)
    }

    /**
     * Registers **light** font
     */
    private fun registerLight() {
        light =
            Font.createFont(Font.TRUETYPE_FONT, File("files${FileSystems.getDefault().separator}fonts${FileSystems.getDefault().separator}Product-Sans-Light.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(light)
    }

    /**
     * Registers **bold** font
     */
    private fun registerBold() {
        bold =
            Font.createFont(Font.TRUETYPE_FONT, File("files${FileSystems.getDefault().separator}fonts${FileSystems.getDefault().separator}Product-Sans-Bold.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(bold)
    }

}