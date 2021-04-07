package userinterface.popups

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

class ColorPickerPopup(
    val exec: (red: Int, green: Int, blue: Int) -> Unit,
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

    override var open = true

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        if (open) {
            g.color = Color.decode("#555555")
            g.fillRoundRect(250 - 50, 220 + 30, 850, 160, 25, 25)

            for (widget in widgets) {
                widget.paint(g, g2, observer)
            }

            // Preview

            g.color = ColorPalette.foreground
            g.fillRoundRect(220, 270, 110, 110, 25, 25)

            g.color = Color(redSlider.value, greenSlider.value, blueSlider.value)
            g.fillRoundRect(225, 275, 100, 100, 25, 25)

            // Apply button

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

            // Cancel button

            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (hoveredCancel) {
                    1f
                } else {
                    0.6f
                }
            )
            g2.drawString("Cancel", 970, 390)

        }
    }

    override fun mouseClicked(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mouseClicked(x, y)
        }

        if (x in 979..1039 && y in 239..284) {
            exec(redSlider.value, greenSlider.value, blueSlider.value)
            open = false
            WindowHandler.popup = null
        }else if (x in 969..1044 && y in 349..394) {
            open = false
            WindowHandler.popup = null
        }

    }

    override fun mouseMoved(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mouseMoved(x, y)
        }

        hoveredApply = (x in 979..1039 && y in 239..284)
        hoveredCancel = (x in 969..1044 && y in 349..394)

    }

    override fun dragMouse(x: Int, y: Int) {
        for (widget in widgets) {
            widget.dragMouse(x, y)
        }
    }

    override fun mousePressed(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mousePressed(x, y)
        }
    }

}