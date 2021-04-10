package userinterface.widgets

import utils.resetOpacity
import utils.setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Widget
import userinterface.WindowHandler
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class ColorSliderWidget(
    private val x: Int,
    private val y: Int,
    private val color: Color,
    private val label: String,
    oldValue: Int = 0
) : Widget() {

    /**
     * **<code>x-Coordinate</code>** of dot
     */
    private var dotX = 0

    /**
     * Value of this slider
     */
    var value = 0

    /**
     * Whether the slider is dragged or not
     */
    private var dragging = false

    init {
        // Applying oldValue

        value = oldValue
        dotX = value * 2
    }

    /**
     * Whether the slider is available or not *(disabled)*
     */
    var available = true

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        // Setting opacity dependents on available

        if(!available) {
            g2.setOpacity(0.4f)
        }else {
            g2.resetOpacity()
        }

        g2.color = ColorPalette.foreground
        if (available) {
            g2.setOpacity(0.6f)
        }

        // Draw line and dot

        g2.fillRoundRect(x, y + 10, 510 + 19, 5, 5, 5)
        g2.fillOval(x + dotX, y + 4, 19, 19)

        if (available) {
            g2.resetOpacity()
        }

        g2.color = color
        g2.fillOval(x + dotX + 2, y + 6, 15, 15)

        // Draw label

        g.font = CustomFont.regular?.deriveFont(26f)
        g.drawString(
            label, if (label == "G") {
                x - 27
            } else {
                x - 25
            }, y + 22
        )

        // Draw label

        value = dotX / 2
        g.drawString(value.toString(), x + 510 + 25, y + 22)

    }

    override fun mouseClicked(x: Int, y: Int) {
        dragging = false
    }

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {
        if (dragging) {
            WindowHandler.frame.repaint()
            // Updating dotX to current drag position

            val target = x - this.x
            if (target <= (255 * 2) && target > 0) {
                dotX = target
            }
        }
    }

    override fun mousePressed(x: Int, y: Int) {
        dragging = x in (this.x + dotX)..((this.x + dotX) + 510) && y in (this.y + 4 - 20)..(this.y + 23 - 20)
    }

    override fun keyReleased(char: Char, keyCode: Int) {}

}