package openrgb.effects

import openrgb.Effect
import java.awt.Color

class GradientEffect(override val fps: Int, startHex: String, endHex: String) : Effect() {

    private var startColor = Color.decode(startHex)
    var endColor = Color.decode(endHex)

    var originalStartColor = Color.white
    var originalEndColor = Color.white

    private var ratio = 0f

    override var animation = true

    override var thread = Thread {

        originalStartColor = Color.decode(startHex)
        originalEndColor = Color.decode(endHex)

        while (true) {
            Thread.sleep((1000 / fps).toLong())

            if (ratio >= 1f) {
                ratio = 0f

                val tempStartColor = startColor
                startColor = endColor
                endColor = tempStartColor
            }

            ratio += 0.01f
            val color = blend(startColor, endColor, ratio)

            colorHex = String.format("#%02x%02x%02x", color.red, color.green, color.blue)
        }
    }

    private fun blend(c1: Color, c2: Color, ratio: Float): Color {

        var ratio = ratio
        if (ratio > 1f) ratio = 1f else if (ratio < 0f) ratio = 0f
        val iRatio = 1.0f - ratio
        val i1 = c1.rgb
        val i2 = c2.rgb
        val a1 = i1 shr 24 and 0xff
        val r1 = i1 and 0xff0000 shr 16
        val g1 = i1 and 0xff00 shr 8
        val b1 = i1 and 0xff
        val a2 = i2 shr 24 and 0xff
        val r2 = i2 and 0xff0000 shr 16
        val g2 = i2 and 0xff00 shr 8
        val b2 = i2 and 0xff
        val a = (a1 * iRatio + a2 * ratio).toInt()
        val r = (r1 * iRatio + r2 * ratio).toInt()
        val g = (g1 * iRatio + g2 * ratio).toInt()
        val b = (b1 * iRatio + b2 * ratio).toInt()
        return Color(a shl 24 or (r shl 16) or (g shl 8) or b)
    }
}