package userinterface.widgets

import resetOpacity
import setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Widget
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class ColorSliderWidget(
    private val x: Int,
    private val y: Int,
    private val color: Color,
    private val label: String,
    private val oldValue: Int = 1
) :
    Widget() {

    private var dotX = 1
    var value = 1
    private var dragging = false

    init {
        value = oldValue
        dotX = if(value == 1) {
            1
        }else {
            value * 2
        }
    }

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        g2.color = ColorPalette.foreground
        g2.setOpacity(0.6f)

        g2.fillRoundRect(x, y + 10, 510 + 19, 5, 5, 5)
        g2.fillOval(x + dotX, y + 4, 19, 19)
        g2.resetOpacity()

        g2.color = color
        g2.fillOval(x + dotX + 2, y + 6, 15, 15)

        g.font = CustomFont.regular?.deriveFont(26f)
        g.drawString(
            label, if (label == "G") {
                x - 27
            } else {
                x - 25
            }, y + 22
        )

        value = if (dotX == 1) {
            1
        } else {
            dotX / 2
        }
        g.drawString(value.toString(), x + 510 + 25, y + 22)

    }

    override fun mouseClicked(x: Int, y: Int) {
        dragging = false
    }

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {
        if (dragging) {
            val target = x - this.x
            if (target <= (255 * 2) && target > 1) {
                dotX = target
            }
        }
    }

    override fun mousePressed(x: Int, y: Int) {
        dragging = x in (this.x + dotX)..((this.x + dotX) + 510) && y in (this.y + 4 - 20)..(this.y + 23 - 20)
    }

}