package utils

import java.awt.AlphaComposite
import java.awt.Graphics2D

fun Graphics2D.setOpacity(opacity: Float) {
    this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)
}

fun Graphics2D.resetOpacity() {
    this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
}