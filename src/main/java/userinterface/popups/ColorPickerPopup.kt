package userinterface.popups

import setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Popup
import userinterface.widgets.ColorSliderWidget
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class ColorPickerPopup(
    val exec: (red: Int, green: Int, blue: Int) -> Unit,
    private val oldRed: Int = 1,
    private val oldGreen: Int = 1,
    private val oldBlue: Int = 1
) : Popup() {

    private var redSlider = ColorSliderWidget(415, 275, Color.red, "R", oldRed)
    private var greenSlider = ColorSliderWidget(415, 315, Color.green, "G", oldGreen)
    private var blueSlider = ColorSliderWidget(415, 355, Color.blue, "B", oldBlue)

    private var widgets = listOf(redSlider, greenSlider, blueSlider)

    private var hoveredClose = false
    private var open = true

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        if (open) {
            g.color = Color.decode("#555555")
            g.fillRoundRect(250 - 50, 220 + 30, 850, 160, 25, 25)

            for (widget in widgets) {
                widget.paint(g, g2, observer)
            }

            /* Preview */

            g.color = ColorPalette.foreground
            g.fillRoundRect(250, 270, 110, 110, 25, 25)

            g.color = Color(redSlider.value, greenSlider.value, blueSlider.value)
            g.fillRoundRect(255, 275, 100, 100, 25, 25)

            /* Done button */
            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (hoveredClose) {
                    1f
                } else {
                    0.6f
                }
            )

            g2.font = CustomFont.light?.deriveFont(24f)
            g2.drawString("Apply", 980, 280)
        }
    }

    override fun mouseClicked(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mouseClicked(x, y)
        }

        if (x in 979..1039 && y in 239..284) {
            exec(redSlider.value, greenSlider.value, blueSlider.value)
            open = false
        }

    }

    override fun mouseMoved(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mouseMoved(x, y)
        }

        hoveredClose = (x in 979..1039 && y in 239..284)

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