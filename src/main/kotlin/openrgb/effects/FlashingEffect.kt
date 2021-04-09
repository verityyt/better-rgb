package openrgb.effects

import openrgb.Effect
import openrgb.EffectsEnum
import java.awt.Color

/**
 * **FPS = FLASH DURATION**
 */
class FlashingEffect(override val fps: Int, startHex: String, endHex: String) : Effect() {


    /**
     * Start color of current blend
     */
    override var startColor = Color.decode(startHex)

    /**
     * End color of current blend
     */
    override var endColor = Color.decode(endHex)

    /**
     * Original start color *(does not change until effect is delete)*
     */
    override var originalStartColor = Color.white

    /**
     * Original end color *(does not change until effect is delete)*
     */
    override var originalEndColor = Color.white

    /**
     * Whether the effect is a animation or not *(static)*
     */
    override var animation = true

    /**
     * [EffectsEnum] of the effect
     */
    override var enumType: EffectsEnum = EffectsEnum.FLASHING

    init {
        colorHex = String.format("#%02x%02x%02x", startColor.red, startColor.green, startColor.blue)
    }

    private var sleepTimeBetween = listOf(100, 125, 250, 500, 750, 1000, 1250, 1500, 1750, 2000, 2250, 2500, 2750, 3000)

    override var thread = Thread {

        originalStartColor = Color.decode(startHex)
        originalEndColor = Color.decode(endHex)

        while (true) {
            Thread.sleep(sleepTimeBetween.random().toLong())

            colorHex = String.format("#%02x%02x%02x", endColor.red, endColor.green, endColor.blue)

            Thread.sleep(fps.toLong())

            colorHex = String.format("#%02x%02x%02x", startColor.red, startColor.green, startColor.blue)

        }
    }
}