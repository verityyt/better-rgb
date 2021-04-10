package userinterface.popups

import userinterface.*
import utils.setOpacity
import userinterface.widgets.TextFieldWidget
import utils.Logger
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class CustomLabelPopup(
    val exec: (input: String) -> Unit,
    private val zoneName: String
) : Popup() {

    var textField = TextFieldWidget(340, 290, 550, 30, zoneName, "Custom Label | $zoneName")

    private var widgets = listOf<Widget>(textField)

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

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {
        if (open) {
            g.color = Color.decode("#555555")
            g.fillRoundRect(300, 230, 650, 150, 25, 25)

            for (widget in widgets) {
                widget.paint(g, g2, observer)
            }

            // Title
            g2.color = ColorPalette.foreground
            g2.font = CustomFont.regular?.deriveFont(22f)
            g2.drawString("Set custom label for $zoneName:", 315, 260)

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
            g2.drawString("Apply", 875, 260)

            // Cancel button

            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (hoveredCancel) {
                    1f
                } else {
                    0.6f
                }
            )
            g2.drawString("Cancel", 865, 360)

        }
    }

    override fun mouseClicked(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mouseClicked(x, y)
        }

        if (x in 875..936 && y in 220..260) {
            Logger.`interface`("Clicked on \"Apply\" button open ColorPickerPopup!")

            exec(textField.content)
            open = false

            WindowHandler.popup = null
        } else if (x in 865..926 && y in 320..360) {
            Logger.`interface`("Clicked on \"Cancel\" button open ColorPickerPopup!")

            open = false

            WindowHandler.popup = null
        }

    }

    override fun mouseMoved(x: Int, y: Int) {
        for (widget in widgets) {
            widget.mouseMoved(x, y)
        }

        hoveredApply = (x in 875..936 && y in 220..260)
        hoveredCancel = (x in 865..926 && y in 320..360)

    }

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

    override fun keyReleased(char: Char, keyCode: Int) {
        for (widget in widgets) {
            widget.keyReleased(char, keyCode)
        }
    }

}