package userinterface.popups

import openrgb.EffectsEnum
import utils.resetOpacity
import utils.setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Popup
import userinterface.WindowHandler
import userinterface.widgets.ColorSliderWidget
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class EffectPickerPopup(
    val exec: (red: Int, green: Int, blue: Int, effect: EffectsEnum) -> Unit,
    oldEffect: EffectsEnum = EffectsEnum.STATIC,
    oldRed: Int = 0,
    oldGreen: Int = 0,
    oldBlue: Int = 0
) : Popup() {

    private var redSlider = ColorSliderWidget(385, 275, Color.red, "R", oldRed)
    private var greenSlider = ColorSliderWidget(385, 315, Color.green, "G", oldGreen)
    private var blueSlider = ColorSliderWidget(385, 355, Color.blue, "B", oldBlue)

    private var widgets = listOf(redSlider, greenSlider, blueSlider)

    /**
     * Whether the **<code>Apply</code> button** is hovered or not
     */
    private var hoveredApply = false

    /**
     * Whether the **<code>Cancel</code> button** is hovered or not
     */
    private var hoveredCancel = false

    /**
     * Whether the popup is open or not
     */
    override var open = true

    /**
     * Current selected [effect][EffectsEnum]
     */
    private var effect: EffectsEnum = oldEffect

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        if (open) {
            g.color = Color.decode("#555555")
            g.fillRoundRect(250 - 50, 220 + 30, 850, 200, 25, 25)

            if (effect.needSecondary) {
                g2.resetOpacity()

                for (widget in widgets) {
                    widget.paint(g, g2, observer)
                    widget.available = true // Enabling color slider if secondary color is needed
                }

                // Preview

                g.color = ColorPalette.foreground
                g.fillRoundRect(220, 270, 110, 110, 25, 25)

                g.color = Color(redSlider.value, greenSlider.value, blueSlider.value)
                g.fillRoundRect(225, 275, 100, 100, 25, 25)


            } else {

                for (widget in widgets) {
                    widget.paint(g, g2, observer)
                    widget.available = false // Disabling color slider if secondary color is NOT needed
                }

                // Preview

                g2.setOpacity(0.4f)
                g2.color = ColorPalette.foreground
                g2.fillRoundRect(220, 270, 110, 110, 25, 25)

                g2.color = Color(redSlider.value, greenSlider.value, blueSlider.value)
                g2.fillRoundRect(225, 275, 100, 100, 25, 25)

            }

            // Apply Button

            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (hoveredApply) {
                    1f
                } else {
                    0.6f
                }
            )

            g2.font = CustomFont.light?.deriveFont(24f)
            g2.drawString("Apply", 980, 280)

            // Cancel Button

            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (hoveredCancel) {
                    1f
                } else {
                    0.6f
                }
            )
            g2.drawString("Cancel", 970, 430)

            // Effects

            when (effect) {
                EffectsEnum.STATIC -> {
                    g2.resetOpacity()
                    g2.color = ColorPalette.foreground
                    g2.fillRoundRect(220, 400, 75, 35, 10, 10)

                    g.color = ColorPalette.background
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Static", 225, 426)

                    g2.resetOpacity()
                    g2.color = ColorPalette.background
                    g2.fillRoundRect(220 + 100, 400, 170, 35, 10, 10)

                    g.color = ColorPalette.foreground
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Color Gradient", 225 + 100, 426)

                    g2.resetOpacity()
                    g2.color = ColorPalette.background
                    g2.fillRoundRect(320 + 190, 400, 170, 35, 10, 10)

                    g.color = ColorPalette.foreground
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Rainbow Wave", 325 + 190, 426)
                }
                EffectsEnum.COLOR_GRADIENT -> {
                    g2.resetOpacity()
                    g2.color = ColorPalette.background
                    g2.fillRoundRect(220, 400, 75, 35, 10, 10)

                    g.color = ColorPalette.foreground
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Static", 225, 426)

                    g2.resetOpacity()
                    g2.color = ColorPalette.foreground
                    g2.fillRoundRect(220 + 100, 400, 170, 35, 10, 10)

                    g.color = ColorPalette.background
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Color Gradient", 225 + 100, 426)

                    g2.resetOpacity()
                    g2.color = ColorPalette.background
                    g2.fillRoundRect(320 + 190, 400, 170, 35, 10, 10)

                    g.color = ColorPalette.foreground
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Rainbow Wave", 325 + 190, 426)
                }
                EffectsEnum.RAINBOW_WAVE -> {
                    g2.resetOpacity()
                    g2.color = ColorPalette.background
                    g2.fillRoundRect(220, 400, 75, 35, 10, 10)

                    g.color = ColorPalette.foreground
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Static", 225, 426)

                    g2.resetOpacity()
                    g2.color = ColorPalette.background
                    g2.fillRoundRect(220 + 100, 400, 170, 35, 10, 10)

                    g.color = ColorPalette.foreground
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Color Gradient", 225 + 100, 426)

                    g2.resetOpacity()
                    g2.color = ColorPalette.foreground
                    g2.fillRoundRect(320 + 190, 400, 170, 35, 10, 10)

                    g.color = ColorPalette.background
                    g.font = CustomFont.regular?.deriveFont(24f)
                    g.drawString("Rainbow Wave", 325 + 190, 426)
                }
            }

        }
    }

    override fun mouseClicked(x: Int, y: Int) {
        if (effect.needSecondary) {
            for (widget in widgets) {
                widget.mouseClicked(x, y)
            }
        }

        if (x in 979..1039 && y in 239..284) {
            // Apply Button

            exec(redSlider.value, greenSlider.value, blueSlider.value, effect)
            open = false
            WindowHandler.popup = null
        } else if (x in 969..1044 && y in 379..484) {
            // Cancel Button
            open = false
            WindowHandler.popup = null
        } else if (x in 220..275 && y in 370..435) {
            effect = EffectsEnum.STATIC
        } else if (x in 320..430 && y in 370..435) {
            effect = EffectsEnum.COLOR_GRADIENT
        } else if (x in 510..680 && y in 370..435) {
            effect = EffectsEnum.RAINBOW_WAVE
        }

    }

    override fun mouseMoved(x: Int, y: Int) {
        if (effect.needSecondary) {
            for (widget in widgets) {
                widget.mouseMoved(x, y)
            }
        }

        hoveredApply = (x in 979..1039 && y in 239..284)
        hoveredCancel = (x in 969..1044 && y in 379..484)
    }

    override fun dragMouse(x: Int, y: Int) {
        if (effect.needSecondary) {
            for (widget in widgets) {
                widget.dragMouse(x, y)
            }
        }
    }

    override fun mousePressed(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mousePressed(x, y)
        }
    }

}