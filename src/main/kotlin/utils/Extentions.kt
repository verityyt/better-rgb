package utils

import java.awt.AlphaComposite
import java.awt.Graphics2D

/**
 * Extention function for [Graphics2D] to set opacity/[AlphaComposite] to given float
 */
fun Graphics2D.setOpacity(opacity: Float) {
    this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)
}

/**
 * Extention function for [Graphics2D] to reset opacity/[AlphaComposite] to 1.0f
 */
fun Graphics2D.resetOpacity() {
    this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
}