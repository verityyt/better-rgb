package userinterface.screens

import openrgb.Effect
import openrgb.EffectsEnum
import openrgb.OpenRGBManager
import openrgb.effects.GradientEffect
import openrgb.effects.RainbowEffect
import openrgb.effects.StaticEffect
import utils.resetOpacity
import utils.setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Screen
import userinterface.WindowHandler
import userinterface.popups.ColorPickerPopup
import userinterface.popups.EffectPickerPopup
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver
import java.io.File
import javax.imageio.ImageIO

class DeviceZoneScreen(private val deviceName: String, private val deviceIndex: Int) : Screen() {

    private var deviceZones: HashMap<Int, String> = OpenRGBManager.getDeviceZoneNames(deviceIndex)

    private val zoneColorButton = HashMap<Int, ZoneConfigurationButton>()

    private val zoneEffects = HashMap<Int, EffectsEnum>()

    private val zoneCurrentEffect = HashMap<Int, Effect>()

    private var drawZoneY = 175

    init {
        for (zoneIndex in 0 until deviceZones.size) {
            var zoneName = deviceZones[zoneIndex]!!

            if (zoneName.length > 25) {
                zoneName = zoneName.substring(0, 23) + "..."
                zoneName = zoneName.substring(0, 23) + "..."
            }

            zoneColorButton[zoneIndex] = ZoneConfigurationButton(
                zoneIndex, if (zoneIndex <= 8) {
                    566 - 125
                } else {
                    996 - 125
                }, drawZoneY - 19
            )

            if (zoneIndex == 8) {
                drawZoneY = 175
            } else {
                drawZoneY += 50
            }
        }

        drawZoneY = 175
    }

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(24f)
        g.drawString("$deviceName:", 175, 125)

        for (zoneIndex in 0 until deviceZones.size) {
            var zoneName = deviceZones[zoneIndex]!!

            if (zoneName.length > 25) {
                zoneName = zoneName.substring(0, 23) + "..."
                zoneName = zoneName.substring(0, 23) + "..."
            }

            g.color = ColorPalette.foreground
            g.font = CustomFont.regular?.deriveFont(24f)

            g.drawString(
                zoneName, if (zoneIndex <= 8) {
                    225
                } else {
                    660
                }, drawZoneY
            )

            g2.resetOpacity()

            if (zoneEffects.containsKey(zoneIndex)) {
                if (!zoneEffects[zoneIndex]!!.needPrimary) {
                    g2.setOpacity(0.4f)

                    g2.fillRoundRect(
                        if (zoneIndex <= 8) {
                            557 - 125
                        } else {
                            987 - 125
                        }, drawZoneY - 27, 35, 35, 10, 10
                    )

                    g2.color = Color.decode(zoneColorButton[zoneIndex]!!.colorHex)
                    g2.fillRoundRect(
                        if (zoneIndex <= 8) {
                            560 - 125
                        } else {
                            990 - 125
                        }, drawZoneY - 24, 29, 29, 10, 10
                    )
                } else {
                    g.fillRoundRect(
                        if (zoneIndex <= 8) {
                            557 - 125
                        } else {
                            987 - 125
                        }, drawZoneY - 27, 35, 35, 10, 10
                    )

                    g.color = Color.decode(zoneColorButton[zoneIndex]!!.colorHex)
                    g.fillRoundRect(
                        if (zoneIndex <= 8) {
                            560 - 125
                        } else {
                            990 - 125
                        }, drawZoneY - 24, 29, 29, 10, 10
                    )
                }
            } else {
                g.fillRoundRect(
                    if (zoneIndex <= 8) {
                        557 - 125
                    } else {
                        987 - 125
                    }, drawZoneY - 27, 35, 35, 10, 10
                )

                g.color = Color.decode(zoneColorButton[zoneIndex]!!.colorHex)
                g.fillRoundRect(
                    if (zoneIndex <= 8) {
                        560 - 125
                    } else {
                        990 - 125
                    }, drawZoneY - 24, 29, 29, 10, 10
                )
            }

            g2.resetOpacity()
            g.color = ColorPalette.foreground

            g.fillRoundRect(
                if (zoneIndex <= 8) {
                    610 - 125
                } else {
                    1040 - 125
                }, drawZoneY - 26, 100, 35, 10, 10
            )

            g.font = CustomFont.regular?.deriveFont(22f)
            g.color = ColorPalette.background
            g.drawString(
                "Effect", if (zoneIndex <= 8) {
                    615 - 125
                } else {
                    1045 - 125
                }, drawZoneY
            )

            g.drawImage(
                ImageIO.read(File("files\\images\\devices\\configure.png")), if (zoneIndex <= 8) {
                    615 - 60
                } else {
                    1045 - 60
                }, drawZoneY - 20,
                observer
            )

            if (zoneIndex == 8) {
                drawZoneY = 175
            } else {
                drawZoneY += 50
            }
        }

        drawZoneY = 175

    }

    override fun mouseClicked(x: Int, y: Int) {
        for (entry in zoneColorButton) {
            val button = entry.value

            if (x in (button.x - 1) until (button.x + 24) && y in (button.y - 50) until (button.y)) {

                if (zoneEffects.containsKey(button.zoneIndex)) {
                    if (zoneEffects[button.zoneIndex]!!.needPrimary) {
                        handleColorPicker(button)
                    }
                } else {
                    handleColorPicker(button)
                }
            } else if (x in (button.x + 53) until (button.x + 153) && y in (button.y - 50) until (button.y)) {
                WindowHandler.popup = EffectPickerPopup(
                    { red: Int, green: Int, blue: Int, effect: EffectsEnum ->
                        zoneEffects[button.zoneIndex] = effect

                        when (effect) {
                            EffectsEnum.STATIC -> {
                                val colorHex = zoneColorButton[button.zoneIndex]!!.colorHex

                                val effect = StaticEffect(colorHex)

                                OpenRGBManager.updateZoneColor(
                                    deviceIndex,
                                    button.zoneIndex,
                                    effect
                                )

                                zoneCurrentEffect[button.zoneIndex] = effect

                                button.colorHex = colorHex
                            }
                            EffectsEnum.COLOR_GRADIENT -> {
                                val colorHex = zoneColorButton[button.zoneIndex]!!.colorHex

                                val effect =
                                    GradientEffect(60, colorHex, String.format("#%02x%02x%02x", red, green, blue))

                                OpenRGBManager.updateZoneColor(
                                    deviceIndex,
                                    button.zoneIndex,
                                    effect
                                )

                                zoneCurrentEffect[button.zoneIndex] = effect
                            }
                            EffectsEnum.RAINBOW_WAVE -> {
                                val effect = RainbowEffect(60)

                                OpenRGBManager.updateZoneColor(
                                    deviceIndex,
                                    button.zoneIndex,
                                    effect
                                )

                                zoneCurrentEffect[button.zoneIndex] = effect
                            }
                        }
                    },
                    zoneEffects[button.zoneIndex] ?: EffectsEnum.STATIC,
                    if (zoneCurrentEffect[button.zoneIndex] == null) {
                        0
                    } else {
                        val oldEffect = zoneCurrentEffect[button.zoneIndex]
                        if (oldEffect is GradientEffect) {
                            val effect = zoneCurrentEffect[button.zoneIndex] as GradientEffect
                            effect.originalEndColor.red
                        } else {
                            0
                        }
                    },
                    if (zoneCurrentEffect[button.zoneIndex] == null) {
                        0
                    } else {
                        val oldEffect = zoneCurrentEffect[button.zoneIndex]
                        if (oldEffect is GradientEffect) {
                            val effect = zoneCurrentEffect[button.zoneIndex] as GradientEffect
                            effect.originalEndColor.green
                        } else {
                            0
                        }
                    },
                    if (zoneCurrentEffect[button.zoneIndex] == null) {
                        0
                    } else {
                        val oldEffect = zoneCurrentEffect[button.zoneIndex]
                        if (oldEffect is GradientEffect) {
                            val effect = zoneCurrentEffect[button.zoneIndex] as GradientEffect
                            effect.originalEndColor.blue
                        } else {
                            0
                        }
                    }
                )
            }
        }
    }

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

    private fun handleColorPicker(button: ZoneConfigurationButton) {

        val color = Color.decode(button.colorHex)

        WindowHandler.popup = ColorPickerPopup(
            { red: Int, green: Int, blue: Int ->

                if (zoneEffects.containsKey(button.zoneIndex)) {

                    when (zoneEffects[button.zoneIndex]) {
                        EffectsEnum.STATIC -> {

                            val colorHex = String.format("#%02x%02x%02x", red, green, blue)

                            OpenRGBManager.updateZoneColor(
                                deviceIndex,
                                button.zoneIndex,
                                StaticEffect(colorHex)
                            )

                            button.colorHex = colorHex
                        }
                        EffectsEnum.COLOR_GRADIENT -> {
                            if (zoneCurrentEffect.containsKey(button.zoneIndex)) {
                                if (zoneCurrentEffect[button.zoneIndex] is GradientEffect) {
                                    val oldEffect = zoneCurrentEffect[button.zoneIndex] as GradientEffect
                                    val startHex = String.format("#%02x%02x%02x", red, green, blue)
                                    val endColor = oldEffect.endColor
                                    val endHex =
                                        String.format("#%02x%02x%02x", endColor.red, endColor.green, endColor.blue)

                                    val effect = GradientEffect(60, startHex, endHex)

                                    OpenRGBManager.updateZoneColor(
                                        deviceIndex,
                                        button.zoneIndex,
                                        effect
                                    )
                                } else {
                                    OpenRGBManager.updateZoneColor(
                                        deviceIndex,
                                        button.zoneIndex,
                                        GradientEffect(60, String.format("#%02x%02x%02x", red, green, blue), "#FFFFFF")
                                    )
                                }
                            } else {
                                OpenRGBManager.updateZoneColor(
                                    deviceIndex,
                                    button.zoneIndex,
                                    GradientEffect(60, String.format("#%02x%02x%02x", red, green, blue), "#FFFFFF")
                                )
                            }

                            button.colorHex = String.format("#%02x%02x%02x", red, green, blue)
                        }
                        EffectsEnum.RAINBOW_WAVE -> {
                            OpenRGBManager.updateZoneColor(
                                deviceIndex,
                                button.zoneIndex,
                                RainbowEffect(60)
                            )
                        }
                    }

                } else {
                    val colorHex = String.format("#%02x%02x%02x", red, green, blue)

                    OpenRGBManager.updateZoneColor(
                        deviceIndex,
                        button.zoneIndex,
                        StaticEffect(colorHex)
                    )

                    button.colorHex = colorHex
                }

                WindowHandler.popup = null
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