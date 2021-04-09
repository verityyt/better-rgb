package userinterface.screens

import openrgb.Effect
import openrgb.EffectsEnum
import openrgb.OpenRGBManager
import openrgb.effects.*
import userinterface.*
import utils.resetOpacity
import utils.setOpacity
import userinterface.popups.ColorPickerPopup
import userinterface.popups.EffectPickerPopup
import utils.ColorPartEnum
import utils.Logger
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.ImageObserver
import java.io.File
import java.nio.file.FileSystems
import javax.imageio.ImageIO

class DeviceZoneScreen(private val deviceName: String, private val deviceIndex: Int) : Screen() {

    /**
     * List of all zones of this device by zoneIndex
     * > **Key**: ZoneIndex
     *
     * > **Value**: ZoneName
     */
    private var deviceZones: HashMap<Int, String> = OpenRGBManager.getDeviceZones(deviceIndex)

    /**
     * List of all zones of this device with [Rectangle]
     * **Key**: ZoneIndex
     *
     * **Value**: HashMap
     *
     * > **Key**: ZoneName
     *
     * > **Value**: [Pair] (x, y, w, h of text)
     */
    private var deviceZonePosition = HashMap<Int, Pair<String, Rectangle>>()

    /**
     * List of all [ZoneConfigurationButton]s of this device by zoneIndex
     * > **Key**: ZoneIndex
     *
     * > **Value**: [ZoneConfigurationButton]
     */
    private val zoneColorButton = HashMap<Int, ZoneConfigurationButton>()

    /**
     * List of all zone effects *(as [EffectsEnum])* of this device by zoneIndex
     * > **Key**: ZoneIndex
     *
     * > **Value**: [EffectsEnum]
     */
    private val zoneEffects = HashMap<Int, EffectsEnum>()

    /**
     * List of all zones current effect (as [Effect]) of this device by zoneIndex
     * > **Key**: ZoneIndex
     *
     * > **Value**: [Effect]
     */
    private val zoneCurrentEffect = HashMap<Int, Effect>()

    /**
     * Draw **<code>y-Coordinate</code>** of the currently drawn zone name and button
     */
    private var drawZoneY = 175

    init {
        for (zoneIndex in 0 until deviceZones.size) {
            val customName = CustomLabels.getLabel(deviceName, zoneIndex.toString())
            var zoneName = if (customName == "") {
                deviceZones[zoneIndex]!!
            } else {
                customName
            }

            if (zoneName.length > 16) {
                zoneName = zoneName.substring(0, 15) + "..." // Update zone name if its to long
            }

            // Save ZoneConfigurationButton in zoneColorButton by zoneIndex

            zoneColorButton[zoneIndex] = ZoneConfigurationButton(
                zoneIndex, if (zoneIndex <= 8) {
                    566 - 125
                } else if (zoneIndex <= 17) {
                    996 - 125
                } else {
                    1210
                }, drawZoneY - 19
            )

            // Updating drawZoneY for next zone

            if (zoneIndex == 8) {
                drawZoneY = 175
            } else if (zoneIndex == 17) {
                drawZoneY = 1210
            } else {
                drawZoneY += 50
            }
        }

        drawZoneY = 175
    }

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        // Draw device name

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(24f)
        g.drawString("$deviceName:", 175, 125)

        for (zoneIndex in 0 until deviceZones.size) {
            val customName = CustomLabels.getLabel(deviceName, zoneIndex.toString())
            var zoneName = if (customName == "") {
                deviceZones[zoneIndex]!!
            } else {
                customName
            }

            // Draw zone name

            g.color = ColorPalette.foreground
            g.font = CustomFont.regular?.deriveFont(24f)



            if (zoneName.length > 16) {
                zoneName = zoneName.substring(0, 15) + "..." // Update zone name if its to long
            }

            deviceZonePosition[zoneIndex] = Pair(
                deviceZones[zoneIndex]!!, Rectangle(
                    if (zoneIndex <= 8) {
                        225
                    } else if (zoneIndex <= 17) {
                        660
                    } else {
                        1210
                    }, drawZoneY - 25, g.fontMetrics.stringWidth(zoneName), 30
                )
            )



            g.drawString(
                zoneName, if (zoneIndex <= 8) {
                    225
                } else if (zoneIndex <= 17) {
                    660
                } else {
                    1210
                }, drawZoneY
            )

            g2.resetOpacity()

            // Draw color preview

            if (zoneEffects.containsKey(zoneIndex) && !zoneEffects[zoneIndex]!!.needPrimary) {
                g2.setOpacity(0.4f)
            } else {
                g2.resetOpacity()
            }

            g2.fillRoundRect(
                if (zoneIndex <= 8) {
                    557 - 125
                } else if (zoneIndex <= 17) {
                    987 - 125
                } else {
                    1210
                }, drawZoneY - 27, 35, 35, 10, 10
            )

            g2.color = Color.decode(zoneColorButton[zoneIndex]!!.colorHex)
            g2.fillRoundRect(
                if (zoneIndex <= 8) {
                    560 - 125
                } else if (zoneIndex <= 17) {
                    990 - 125
                } else {
                    1210
                }, drawZoneY - 24, 29, 29, 10, 10
            )

            // Draw effect button

            g2.resetOpacity()
            g.color = ColorPalette.foreground

            g.fillRoundRect(
                if (zoneIndex <= 8) {
                    610 - 125
                } else if (zoneIndex <= 17) {
                    1040 - 125
                } else {
                    1210
                }, drawZoneY - 26, 100, 35, 10, 10
            )

            g.font = CustomFont.regular?.deriveFont(22f)
            g.color = ColorPalette.background
            g.drawString(
                "Effect", if (zoneIndex <= 8) {
                    615 - 125
                } else if (zoneIndex <= 17) {
                    1045 - 125
                } else {
                    1210
                }, drawZoneY
            )

            g.drawImage(
                ImageIO.read(File("files${FileSystems.getDefault().separator}images${FileSystems.getDefault().separator}devices${FileSystems.getDefault().separator}configure.png")),
                if (zoneIndex <= 8) {
                    615 - 60
                } else if (zoneIndex <= 17) {
                    1045 - 60
                } else {
                    1210
                },
                drawZoneY - 20,
                observer
            )

            // Updating drawDeviceY for next zone

            if (zoneIndex == 8) {
                drawZoneY = 175
            } else if (zoneIndex == 17) {
                drawZoneY = 1210
            } else {
                drawZoneY += 50
            }

            for(entry in deviceZonePosition) {
                val rect = entry.value.second

                g.color = Color.red
                g.drawRect(rect.x, rect.y, rect.width, rect.height)
            }

        }

        drawZoneY = 175

    }

    override fun mouseClicked(x: Int, y: Int) {

        for (entry in deviceZonePosition) {
            val rect = entry.value.second

            if (x in (rect.x)..(rect.x + rect.width) && y in (rect.y - 20)..(rect.y + rect.height)) {
                Logger.`interface`("Clicked at label of \"${entry.value.first}\"!")
            }
        }

        for (entry in zoneColorButton) {
            val button = entry.value

            if (x in (button.x - 1) until (button.x + 24) && y in (button.y - 50) until (button.y)) {
                // Click color preview

                if (zoneEffects.containsKey(button.zoneIndex)) {
                    if (zoneEffects[button.zoneIndex]!!.needPrimary) {
                        handleColorPicker(button)
                    }
                } else {
                    handleColorPicker(button)
                }
            } else if (x in (button.x + 53) until (button.x + 153) && y in (button.y - 50) until (button.y)) {
                // Click effect button
                handleEffectPicker(button)
            }
        }
    }

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

    /**
     * Sets effect to zone by **Device-** and **ZoneIndex**
     */
    private fun setEffect(
        deviceIndex: Int,
        effect: Effect,
        button: ZoneConfigurationButton,
        setButtonColorHex: Boolean = true
    ) {

        OpenRGBManager.updateZoneColor(
            deviceIndex,
            button.zoneIndex,
            effect
        )

        zoneEffects[button.zoneIndex] = effect.enumType
        zoneCurrentEffect[button.zoneIndex] = effect

        if (setButtonColorHex) {
            button.colorHex = effect.colorHex
        }
    }

    /**
     * Opens effect picker and performs effect to specific zone by [ZoneConfigurationButton]
     */
    private fun handleEffectPicker(button: ZoneConfigurationButton) {
        WindowHandler.popup = EffectPickerPopup(
            { red: Int, green: Int, blue: Int, effect: EffectsEnum ->

                when (effect) {
                    EffectsEnum.STATIC -> {
                        setEffect(
                            deviceIndex,
                            StaticEffect(zoneColorButton[button.zoneIndex]!!.colorHex),
                            button
                        )
                    }
                    EffectsEnum.COLOR_GRADIENT -> {
                        setEffect(
                            deviceIndex,
                            GradientEffect(
                                60,
                                zoneColorButton[button.zoneIndex]!!.colorHex,
                                String.format("#%02x%02x%02x", red, green, blue)
                            ),
                            button,
                            false
                        )
                    }
                    EffectsEnum.RAINBOW_WAVE -> {
                        setEffect(
                            deviceIndex,
                            RainbowEffect(60),
                            button,
                            false
                        )
                    }
                    EffectsEnum.BREATHING -> {
                        setEffect(deviceIndex, BreathingEffect(60, button.colorHex), button, true)
                    }
                    EffectsEnum.FLASHING -> {
                        setEffect(
                            deviceIndex,
                            FlashingEffect(
                                60,
                                zoneColorButton[button.zoneIndex]!!.colorHex,
                                String.format("#%02x%02x%02x", red, green, blue)
                            ),
                            button,
                            false
                        )
                    }
                }
            },
            zoneEffects[button.zoneIndex] ?: EffectsEnum.STATIC,
            getOldColorForPart(button, ColorPartEnum.RED),
            getOldColorForPart(button, ColorPartEnum.GREEN),
            getOldColorForPart(button, ColorPartEnum.BLUE)
        )
    }

    /**
     * @return The which int has to be passed to [EffectPickerPopup] as old[{COLOR_NAME}][ColorPartEnum]
     *
     * by **ZoneIndex** and **[ColorPartEnum]**
     */
    private fun getOldColorForPart(button: ZoneConfigurationButton, colorPart: ColorPartEnum): Int {
        if (zoneCurrentEffect[button.zoneIndex] == null) {
            return 0
        } else {
            val effect = zoneCurrentEffect[button.zoneIndex]!!

            if (effect.enumType.needSecondary) {
                if (effect.originalEndColor != null) {
                    when (colorPart) {
                        ColorPartEnum.RED -> return effect.originalEndColor!!.red
                        ColorPartEnum.GREEN -> return effect.originalEndColor!!.green
                        ColorPartEnum.BLUE -> return effect.originalEndColor!!.blue
                    }
                } else {
                    return 0
                }
            } else {
                return 0
            }
        }

        return 0
    }

    private fun handleColorPicker(button: ZoneConfigurationButton) {
        val color = Color.decode(button.colorHex)

        WindowHandler.popup = ColorPickerPopup(
            { red: Int, green: Int, blue: Int ->

                if (zoneEffects.containsKey(button.zoneIndex)) { // Check if an effect is already running on this zone

                    when (zoneEffects[button.zoneIndex]) {
                        EffectsEnum.STATIC -> {
                            setEffect(
                                deviceIndex,
                                StaticEffect(String.format("#%02x%02x%02x", red, green, blue)),
                                button
                            )
                        }
                        EffectsEnum.COLOR_GRADIENT -> {
                            if (zoneCurrentEffect.containsKey(button.zoneIndex)) {
                                val effect = zoneCurrentEffect[button.zoneIndex]!!

                                if (effect.animation && (effect.endColor != null)) {
                                    val startHex = String.format(
                                        "#%02x%02x%02x",
                                        red,
                                        green,
                                        blue
                                    ) // New primary color from color picker
                                    val endColor = effect.endColor // Old secondary color from old effect
                                    val endHex =
                                        String.format("#%02x%02x%02x", endColor!!.red, endColor.green, endColor.blue)

                                    setEffect(deviceIndex, GradientEffect(60, startHex, endHex), button, true)
                                } else {
                                    setEffect(
                                        deviceIndex,
                                        GradientEffect(60, String.format("#%02x%02x%02x", red, green, blue), "#FFFFFF"),
                                        button,
                                        false
                                    )
                                }
                            } else {
                                setEffect(
                                    deviceIndex,
                                    GradientEffect(60, String.format("#%02x%02x%02x", red, green, blue), "#FFFFFF"),
                                    button,
                                    false
                                )
                            }
                        }
                        EffectsEnum.RAINBOW_WAVE -> {
                            setEffect(deviceIndex, RainbowEffect(60), button, false)
                        }
                        EffectsEnum.BREATHING -> {
                            setEffect(
                                deviceIndex,
                                BreathingEffect(60, String.format("#%02x%02x%02x", red, green, blue)),
                                button,
                                true
                            )
                        }
                        EffectsEnum.FLASHING -> {
                            if (zoneCurrentEffect.containsKey(button.zoneIndex)) {
                                val effect = zoneCurrentEffect[button.zoneIndex]!!

                                if (effect.animation && (effect.endColor != null)) {
                                    val startHex = String.format(
                                        "#%02x%02x%02x",
                                        red,
                                        green,
                                        blue
                                    ) // New primary color from color picker
                                    val endColor = effect.endColor // Old secondary color from old effect
                                    val endHex =
                                        String.format("#%02x%02x%02x", endColor!!.red, endColor.green, endColor.blue)

                                    setEffect(deviceIndex, FlashingEffect(60, startHex, endHex), button, true)
                                } else {
                                    setEffect(
                                        deviceIndex,
                                        FlashingEffect(60, String.format("#%02x%02x%02x", red, green, blue), "#FFFFFF"),
                                        button,
                                        false
                                    )
                                }
                            } else {
                                setEffect(
                                    deviceIndex,
                                    FlashingEffect(60, String.format("#%02x%02x%02x", red, green, blue), "#FFFFFF"),
                                    button,
                                    false
                                )
                            }
                        }
                    }

                } else {
                    setEffect(deviceIndex, StaticEffect(String.format("#%02x%02x%02x", red, green, blue)), button)
                }
            }, color.red, color.green, color.blue
        )
    }

}

class ZoneConfigurationButton(
    val zoneIndex: Int,
    val x: Int,
    val y: Int,
    var colorHex: String = "#000000"
)