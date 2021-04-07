package userinterface.screens

import openrgb.OpenRGBManager
import utils.resetOpacity
import utils.setOpacity
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Screen
import userinterface.WindowHandler
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver
import java.io.File
import javax.imageio.ImageIO

class DevicesScreen : Screen() {

    /**
     * Whether the **<code>Try again</code>** button is hovered or not
     */
    private var buttonHovered = false

    /**
     * Draw **<code>y-Coordinate</code>** of the currently drawn device name and button
     */
    private var drawDeviceY = 175

    /**
     * List of [DeviceConfigurationButton]s
     */
    private val deviceConfigurationButtons = mutableListOf<DeviceConfigurationButton>()

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        if (OpenRGBManager.connected) { // Check if OpenRGB client is connected

            for (deviceIndex in 0 until OpenRGBManager.deviceByIndex.size) {
                var deviceName = OpenRGBManager.deviceByIndex[deviceIndex]!!

                if (deviceName.length > 25) {
                    deviceName = deviceName.substring(0, 23) + "..." // Update device name if its to long
                }

                // Draw DeviceName

                g.color = ColorPalette.foreground
                g.font = CustomFont.regular?.deriveFont(24f)

                g.drawString(
                    deviceName, if (deviceIndex <= 8) {
                        225
                    } else {
                        660
                    }, drawDeviceY
                )

                // Draw configure button

                g2.fillRoundRect(
                    if (deviceIndex <= 8) {
                        560
                    } else {
                        990
                    }, drawDeviceY - 25, 35, 35, 10, 10
                )

                g.drawImage(
                    ImageIO.read(File("files\\images\\devices\\configure.png")), if (deviceIndex <= 8) {
                        566
                    } else {
                        996
                    }, drawDeviceY - 19, 24, 24, observer
                )

                // Adding drawn configure button to list

                deviceConfigurationButtons.add(
                    DeviceConfigurationButton(
                        deviceIndex, if (deviceIndex <= 8) {
                            566
                        } else {
                            996
                        }, drawDeviceY - 19
                    )
                )

                // Updating drawDeviceY for next device

                if (deviceIndex == 8) {
                    drawDeviceY = 175
                } else {
                    drawDeviceY += 50
                }
            }

            // Resetting drawDeviceY
            drawDeviceY = 175

        } else {

            // Warning texts

            g.color = ColorPalette.foreground
            g.font = CustomFont.regular?.deriveFont(36f)
            g.drawString("Could not connect to OpenRGB", 375, 305)

            g.font = CustomFont.regular?.deriveFont(18f)
            g.drawString("Please start SDK Server in OpenRGB, and try again.", 425, 340)

            // Draw 'Try again' button

            g2.color = ColorPalette.foreground
            g2.setOpacity(
                if (buttonHovered) {
                    0.4f
                } else {
                    0.2f
                }
            )
            g2.fillRect(550, 370, 140, 35)
            g2.resetOpacity()

            g.font = CustomFont.regular?.deriveFont(18f)
            g.drawString("TRY AGAIN", 576, 395)

        }

    }

    override fun mouseClicked(x: Int, y: Int) {
        if (x in 549..689 && y in 349..404) {
            // 'Try again' button

            Thread {
                OpenRGBManager.connect()
            }.start()
        }

        for (button in deviceConfigurationButtons) {
            if (x in (button.x - 1) until (button.x + 24) && y in (button.y - 50) until (button.y)) { // Checking which configure button is clicked
                if (WindowHandler.screen !is DeviceZoneScreen) {
                    WindowHandler.screen = OpenRGBManager.deviceZoneScreens[button.deviceIndex] // Setting screen of clicked configure button
                }
            }
        }
    }

    override fun mouseMoved(x: Int, y: Int) {
        buttonHovered = (x in 549..689 && y in 349..404)
    }

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

}

class DeviceConfigurationButton(val deviceIndex: Int, val x: Int, val y: Int)