package userinterface.popups

import openrgb.EffectsEnum
import utils.resetOpacity
import utils.setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Popup
import userinterface.WindowHandler
import userinterface.widgets.ColorSliderWidget
import utils.Logger
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
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

    private var drawEffectX = 220

    private var effectRects = HashMap<EffectsEnum, Rectangle>()

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

            for (effect in EffectsEnum.values()) {
                g2.resetOpacity()

                // Text

                g.color = if (effect == this.effect) {
                    ColorPalette.foreground
                } else {
                    ColorPalette.background
                }

                val rectWidth = g.fontMetrics.stringWidth(effect.displayName) + 10

                g.fillRoundRect(drawEffectX, 400, rectWidth, 35, 10, 10)

                effectRects[effect] = Rectangle(drawEffectX, 400, rectWidth, 35)

                g.color = if (effect == this.effect) {
                    ColorPalette.background
                } else {
                    ColorPalette.foreground
                }

                g.drawString(effect.displayName, drawEffectX + 5, 400 + 25)

                drawEffectX += rectWidth + 10

            }

            drawEffectX = 220

        }
    }

    override fun mouseClicked(x: Int, y: Int) {
        if (effect.needSecondary) {
            for (widget in widgets) {
                widget.mouseClicked(x, y)
            }
        }

        if (x in 979..1039 && y in 239..284) {
            Logger.`interface`("Clicked on \"Apply\" button open ColorPickerPopup!")

            exec(redSlider.value, greenSlider.value, blueSlider.value, effect)
            open = false

            WindowHandler.popup = null
        } else if (x in 969..1044 && y in 379..484) {
            Logger.`interface`("Clicked on \"Cancel\" button open ColorPickerPopup!")

            open = false

            WindowHandler.popup = null
        }

        for (effectRect in effectRects) {
            val rectX = effectRect.value.x
            val rectY = effectRect.value.y
            val rectW = effectRect.value.width
            val rectH = effectRect.value.height

            if (x in (rectX)..(rectX + rectW) && y in (rectY - 25)..(rectY + rectH)) {
                effect = effectRect.key
            }
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

    override fun keyReleased(char: Char, keyCode: Int) {}

}