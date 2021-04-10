package userinterface.widgets

import utils.resetOpacity
import utils.setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Widget
import utils.Logger
import java.awt.BasicStroke
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class TextFieldWidget(
    private val x: Int,
    private val y: Int,
    private val w: Int,
    private val h: Int,
    private var preview: String,
    private var popupName: String
) : Widget() {

    /**
     * Whether the TextField is available or not *(disabled)*
     */
    var available = true

    /**
     * Whether the TextField is focused or not
     */
    var focused = false

    /**
     * Current content of TextField
     */
    var content = preview

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        // Setting opacity dependents on available

        if (!available) {
            g2.setOpacity(0.4f)
        } else {
            g2.resetOpacity()
        }

        g2.color = ColorPalette.background
        g2.fillRoundRect(x, y, w, h, 15, 15)

        if (focused) {
            g2.color = ColorPalette.foreground
            g2.stroke = BasicStroke(2f)
            g2.drawRoundRect(x - 1, y - 1, w + 1, h + 1, 15, 15)
        }

        g2.color = ColorPalette.foreground
        g2.font = CustomFont.regular?.deriveFont(18f)
        g2.drawString(content, x + 5, y + 21)


    }

    override fun mouseClicked(x: Int, y: Int) {
        focused = (x in (this.x)..(this.x + this.w) && y in (this.y - 20)..(this.y + this.h) && available)
        Logger.`interface`(
            "${
                if (focused) {
                    "Focused"
                } else {
                    "Unfocused"
                }
            } TextField in \"$popupName\" popup!"
        )
    }

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

    override fun keyReleased(char: Char, keyCode: Int) {
        if (focused && allowedKeyCodes.contains(keyCode)) {
            if (keyCode == 8) {
                if(content.isNotEmpty()) {
                    content = content.substring(0, content.length - 1)
                }
            } else {
                if (content.length < 36) {
                    content += char.toString()
                }
            }
        }
    }

    private var allowedKeyCodes = listOf(
        8,
        32,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        51,
        52,
        53,
        54,
        55,
        56,
        57,
        59,
        65,
        66,
        67,
        68,
        69,
        70,
        71,
        72,
        73,
        74,
        75,
        76,
        77,
        78,
        79,
        80,
        81,
        82,
        83,
        84,
        85,
        86,
        87,
        88,
        89,
        90,
        92,
        96,
        97,
        98,
        99,
        100,
        101,
        102,
        103,
        104,
        105,
        106,
        107,
        108,
        109,
        110,
        111
    )

}