package userinterface

import userinterface.listener.MouseListener
import userinterface.listener.MouseMotionListener
import java.awt.*
import java.awt.image.ImageObserver
import javax.swing.JFrame

object WindowHandler {

    lateinit var frame: JFrame

    var screen = object : Screen() {
        override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) { }

        override fun mouseClicked(x: Int, y: Int) {
            println("Clicked at $x $y")
        }

        override fun mouseMoved(x: Int, y: Int) {
            println("Moved to $x $y")
        }

    }

    fun open() {

        frame = object : JFrame() {
            override fun paint(g: Graphics) {
                val g2d = g as Graphics2D
                
                g.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
                )
                g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                )
                g.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                )
                g.setRenderingHint(
                    RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_NORMALIZE
                )
                g.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
                )
                g.setRenderingHint(
                    RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON
                )

                g.color = ColorPalette.background
                g.fillRect(0,0,1200,750)

            }
        }

        frame.addMouseListener(MouseListener())
        frame.addMouseMotionListener(MouseMotionListener())

        frame.title = "BetterRGB"
        frame.size = Dimension(1200,750)
        frame.isResizable = false
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true

    }

}