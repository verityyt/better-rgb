package openrgb.effects

import openrgb.Effect
import openrgb.EffectsEnum
import java.awt.Color
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage

class ScreenColorEffect(override val fps: Int) : Effect() {

    /**
     * Index of current wave
     */
    private var currentIndex = 0

    /**
     * Whether the effect is a animation or not *(static)*
     */
    override var animation = true

    /**
     * [EffectsEnum] of the effect
     */
    override var enumType: EffectsEnum = EffectsEnum.SCREEN_COLOR

    override var thread = Thread {
        while (true) {
            Thread.sleep((1000 / fps).toLong())

            val screenshot = Robot().createScreenCapture(Rectangle(Toolkit.getDefaultToolkit().screenSize))
            val color = averageColor(screenshot)
            colorHex = String.format(
                "#%02x%02x%02x",
                color.red,
                color.green,
                color.blue
            )

        }
    }

    private fun averageColor(
        image: BufferedImage
    ): Color {
        val x0 = 0
        val y0 = 0
        val w = image.width
        val h = image.height

        val x1 = x0 + w
        val y1 = y0 + h
        var sumr: Long = 0
        var sumg: Long = 0
        var sumb: Long = 0
        for (x in x0 until x1) {
            for (y in y0 until y1) {
                val pixel = Color(image.getRGB(x, y))
                sumr += pixel.red
                sumg += pixel.green
                sumb += pixel.blue
            }
        }
        val num = w * h

        return Color((sumr / num).toInt(), (sumg / num).toInt(), (sumb / num).toInt())
    }

}