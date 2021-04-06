package openrgb.effects

import openrgb.Effect
import java.awt.Color

class RainbowEffect(override val fps: Int) : Effect() {

    private var rainbowColors = mutableListOf<Color>()
    private var currentIndex = 0

    override var animation = true

    override var thread = Thread {
        generateRainbowColors()

        while (true) {
            Thread.sleep((1000 / fps).toLong())

            if (currentIndex in 0 until (rainbowColors.size - 1)) {
                currentIndex++
            } else {
                currentIndex = 0
            }

            colorHex = String.format(
                "#%02x%02x%02x",
                rainbowColors[currentIndex].red,
                rainbowColors[currentIndex].green,
                rainbowColors[currentIndex].blue
            )
        }
    }

    private fun generateRainbowColors() {
        for (r in 0..99) rainbowColors.add(Color(r * 255 / 100, 255, 0))
        for (g in 100 downTo 1) rainbowColors.add(Color(255, g * 255 / 100, 0))
        for (b in 0..99) rainbowColors.add(Color(255, 0, b * 255 / 100))
        for (r in 100 downTo 1) rainbowColors.add(Color(r * 255 / 100, 0, 255))
        for (g in 0..99) rainbowColors.add(Color(0, g * 255 / 100, 255))
        for (b in 100 downTo 1) rainbowColors.add(Color(0, 255, b * 255 / 100))
        rainbowColors.add(Color(0, 255, 0))
    }

}