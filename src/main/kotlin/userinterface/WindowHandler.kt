package userinterface

import userinterface.listener.KeyListener
import utils.setOpacity
import userinterface.listener.MouseListener
import userinterface.listener.MouseMotionListener
import utils.Logger
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.awt.image.ImageObserver
import java.io.File
import java.nio.file.FileSystems
import javax.imageio.ImageIO
import javax.swing.JFrame

object WindowHandler {

    lateinit var frame: JFrame

    /**
     * Current [screen][Screen]
     */
    var screen: Screen? = null

    /**
     * Current [popup][Popup]
     */
    var popup: Popup? = null
        set(value) {
            if (field == null || !field!!.open) {
                field = value
            }
        }

    /**
     * Current hovered sidebar item *(used to change opacity of hovered sidebar item)*
     */
    var hoveredSidebarItem: SidebarItem? = SidebarItem.HELP

    /**
     * [BufferedImage] that is being painted into by the **paint()** method if the [JFrame][frame]
     */
    var bufferedImage = BufferedImage(1200, 750, BufferedImage.TYPE_INT_BGR)

    fun openWindow() {
        Logger.trace("Setting up userinterface...")

        frame = object : JFrame() {
            override fun paint(g: Graphics) {
                // Create virtual environment (BufferedImage)

                val graphics = bufferedImage.graphics
                val graphics2D = graphics as Graphics2D

                // Set RenderingHints

                graphics.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
                )
                graphics.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                )
                graphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                )
                graphics.setRenderingHint(
                    RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_NORMALIZE
                )
                graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
                )
                graphics.setRenderingHint(
                    RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON
                )

                // Draw background

                graphics.color = ColorPalette.background
                graphics.fillRect(0, 0, 1200, 750)

                // Draw current/target screen

                if (screen == null) {
                    graphics.drawImage(
                        ImageIO.read(File("files${FileSystems.getDefault().separator}images${FileSystems.getDefault().separator}logo${FileSystems.getDefault().separator}logo_512.png")),
                        480,
                        175,
                        256,
                        256,
                        this
                    )

                    graphics.color = ColorPalette.foreground
                    graphics.font = CustomFont.bold?.deriveFont(48f)
                    graphics.drawString("Welcome to BetterRGB", 370, 475)
                } else {
                    screen?.paint(graphics, graphics2D, this)
                }

                popup?.paint(graphics, graphics2D, this)

                // Draw sidebar

                drawSidebarItem(graphics2D, SidebarItem.DEVICES, this)
                drawSidebarItem(graphics2D, SidebarItem.UPDATES, this)
                drawSidebarItem(graphics2D, SidebarItem.HELP, this)

                graphics2D.color = ColorPalette.foreground
                graphics2D.setOpacity(0.4f)
                graphics2D.fillRect(88, 0, 2, 750)

                // Draw BufferedImage

                g.color = ColorPalette.background
                g.fillRect(0, 0, 1200, 750)
                g.drawImage(bufferedImage, 0, 0, 1200, 750, this)

            }
        }

        frame.addMouseMotionListener(object : java.awt.event.MouseMotionListener {
            override fun mouseDragged(e: MouseEvent?) {}

            override fun mouseMoved(e: MouseEvent) {
                val x = e.x
                val y = e.y

                hoveredSidebarItem = if (x in 21..69 && y in 156..224) {
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

        frame.addMouseListener(object : java.awt.event.MouseListener {

            override fun mouseClicked(e: MouseEvent) {
                val x = e.x
                val y = e.y

                if (x in 21..69 && y in 156..224) {
                    Logger.`interface`("Clicked on \"DEVICES\" sidebar item!")
                    screen = SidebarItem.DEVICES.screen
                } else if (x in 21..69 && y in 361..431) {
                    /*screen = SidebarItem.UPDATES.screen*/
                } else if (x in 21..69 && y in 545..585) {
                    Logger.`interface`("Clicked on \"HELP\" sidebar item!")
                    screen = SidebarItem.HELP.screen
                } else if (x < 70) {
                    screen = null
                }

                updateTitle()
            }

            override fun mousePressed(e: MouseEvent?) {}

            override fun mouseReleased(e: MouseEvent?) {}

            override fun mouseEntered(e: MouseEvent?) {}

            override fun mouseExited(e: MouseEvent?) {}

        })

        frame.addMouseListener(MouseListener())
        frame.addMouseMotionListener(MouseMotionListener())
        frame.addKeyListener(KeyListener())

        frame.title = "BetterRGB | Home"
        frame.iconImage =
            ImageIO.read(File("files${FileSystems.getDefault().separator}images${FileSystems.getDefault().separator}logo${FileSystems.getDefault().separator}logo_favicon.png"))

        frame.size = Dimension(1200, 750)
        frame.isResizable = false
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.isVisible = true

        Thread {
            while (true) {
                Thread.sleep(1000 / 10) // Repainting window/frame 10 times in a second -> 10 fps
                frame.repaint()
            }
        }.start()

        Logger.`interface`("Successfully set up userinterface!")

    }

    /**
     * Updates window/frame title
     */
    fun updateTitle() {
        if (screen == null) {
            frame.title = "BetterRGB | Home"
        } else {
            for (item in SidebarItem.values()) {
                if (screen == item.screen) {
                    frame.title = "BetterRGB | ${item.displayName}"
                }
            }
        }
    }

    /**
     * Draws sidebar item
     */
    private fun drawSidebarItem(g2: Graphics2D, item: SidebarItem, observer: ImageObserver) {
        g2.setOpacity(
            if (item == hoveredSidebarItem) {
                if (item.isAvailable) {
                    1.0f
                } else {
                    0.2f
                }
            } else {
                if (!item.isAvailable) {
                    0.2f
                } else if (hoveredSidebarItem == null) {
                    0.4f
                } else {
                    0.4f
                }
            }
        )

        g2.drawImage(
            ImageIO.read(File("files${FileSystems.getDefault().separator}images${FileSystems.getDefault().separator}sidebar${FileSystems.getDefault().separator}${item.imageName}.png")),
            20,
            when (item.index) {
                0 -> {
                    155
                }
                1 -> {
                    360
                }
                else -> {
                    545
                }
            },
            50,
            if (item == SidebarItem.UPDATES) {
                40
            } else {
                50
            },
            observer
        )
    }

}