package userinterface

import setOpacity
import userinterface.listener.MouseListener
import userinterface.listener.MouseMotionListener
import userinterface.screens.HelpScreen
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame

object WindowHandler {

    private lateinit var frame: JFrame

    var screen = HelpScreen()

    var activeSidebarItem: SidebarItem? = SidebarItem.HELP

    fun openWindow() {

        frame = object : JFrame() {
            override fun paint(g: Graphics) {
                val g2 = g as Graphics2D

                /* Create virtual environment (BufferedImage) */

                val bf = BufferedImage(1200, 750, BufferedImage.TYPE_INT_BGR)
                val graphics = bf.createGraphics()
                val graphics2D = graphics as Graphics2D

                /* Set RenderingHints */

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

                /* Draw background */

                graphics.color = ColorPalette.background
                graphics.fillRect(0, 0, 1200, 750)

                /* Draw screen */

                screen.paint(graphics, graphics2D, this)

                /* Draw sidebar */

                drawSidebarItem(graphics2D, SidebarItem.DEVICES, this)
                drawSidebarItem(graphics2D, SidebarItem.UPDATES, this)
                drawSidebarItem(graphics2D, SidebarItem.HELP, this)

                g2.color = ColorPalette.foreground
                g2.setOpacity(0.4f)
                g2.fillRect(88, 0, 2, 750)

                /* Draw BufferedImage */

                g.drawImage(bf, 0, 0, 1200, 750, this)

            }
        }

        frame.addMouseMotionListener(object : java.awt.event.MouseMotionListener {
            override fun mouseDragged(e: MouseEvent?) {}

            override fun mouseMoved(e: MouseEvent) {
                val x = e.x
                val y = e.y

                activeSidebarItem = if (x in 21..69 && y in 156..224) {
                    SidebarItem.DEVICES
                } else if (x in 21..69 && y in 361..431) {
                    SidebarItem.UPDATES
                } else if (x in 21..69 && y in 545..585) {
                    SidebarItem.HELP
                } else {
                    null
                }

            }
        })
        frame.addMouseListener(MouseListener())
        frame.addMouseMotionListener(MouseMotionListener())

        frame.title = "BetterRGB"
        frame.size = Dimension(1200, 750)
        frame.isResizable = false
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true

        Thread {
            while (true) {
                Thread.sleep(1000 / 60)
                frame.repaint()
            }
        }.start()

    }

    fun hideWindow() {
        frame.isVisible = false
    }

    fun showWindow() {
        frame.isVisible = true
    }

    private fun drawSidebarItem(g2: Graphics2D, item: SidebarItem, observer: ImageObserver) {
        g2.setOpacity(
            if (item == activeSidebarItem) {
                if (item.isAvailable) {
                    1.0f
                } else {
                    0.2f
                }
            } else {
                if (!item.isAvailable) {
                    0.2f
                } else if (activeSidebarItem == null) {
                    0.4f
                } else {
                    0.4f
                }
            }
        )

        g2.drawImage(
            ImageIO.read(File("files\\images\\sidebar\\${item.imageName}.png")), 20, when (item.index) {
                0 -> {
                    155
                }
                1 -> {
                    360
                }
                else -> {
                    545
                }
            }, 50, if (item == SidebarItem.UPDATES) {
                40
            } else {
                50
            }, observer
        )
    }

}