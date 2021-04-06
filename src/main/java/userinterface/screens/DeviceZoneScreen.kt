package userinterface.screens

import openrgb.OpenRGBManager
import userinterface.ColorPalette
import userinterface.CustomFont
import userinterface.Screen
import userinterface.WindowHandler
import userinterface.popups.ColorPickerPopup
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class DeviceZoneScreen(val deviceName: String, val deviceIndex: Int) : Screen() {

    private var deviceZones: HashMap<Int, String> = OpenRGBManager.getDeviceZoneNames(deviceIndex)

    private val zoneColorButton = HashMap<Int, ZoneConfigurationButton>()

    private var drawZoneY = 175

    init {
        for (zoneIndex in 0 until deviceZones.size) {
            var zoneName = deviceZones[zoneIndex]!!

            if (zoneName.length > 25) {
                zoneName = zoneName.substring(0, 23) + "..."
                zoneName = zoneName.substring(0, 23) + "..."
            }

            zoneColorButton[zoneIndex] = ZoneConfigurationButton(
                zoneName, zoneIndex, if (zoneIndex <= 8) {
                    566
                } else {
                    996
                }, drawZoneY - 19
            )

            if (zoneIndex == 8) {
                drawZoneY = 175
            } else {
                drawZoneY += 50
            }
        }

        drawZoneY = 175
    }

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {

        g.color = ColorPalette.foreground
        g.font = CustomFont.bold?.deriveFont(24f)
        g.drawString("$deviceName:", 175, 125)

        for (zoneIndex in 0 until deviceZones.size) {
            var zoneName = deviceZones[zoneIndex]!!

            if (zoneName.length > 25) {
                zoneName = zoneName.substring(0, 23) + "..."
                zoneName = zoneName.substring(0, 23) + "..."
            }

            g.color = ColorPalette.foreground
            g.font = CustomFont.regular?.deriveFont(24f)

            g.drawString(
                zoneName, if (zoneIndex <= 8) {
                    225
                } else {
                    660
                }, drawZoneY
            )

            g.fillRoundRect(
                if (zoneIndex <= 8) {
                    557
                } else {
                    987
                }, drawZoneY - 27, 35, 35, 10, 10
            )

            g.color = Color.decode(zoneColorButton[zoneIndex]!!.colorHex)
            g.fillRoundRect(
                if (zoneIndex <= 8) {
                    560
                } else {
                    990
                }, drawZoneY - 24, 29, 29, 10, 10
            )

            g.color = ColorPalette.foreground

            if (zoneIndex == 8) {
                drawZoneY = 175
            } else {
                drawZoneY += 50
            }
        }

        drawZoneY = 175

    }

    override fun mouseClicked(x: Int, y: Int) {
        for (entry in zoneColorButton) {
            val button = entry.value

            if (x in (button.x - 1) until (button.x + 24) && y in (button.y - 50) until (button.y)) {
                val color = Color.decode(button.colorHex)

                WindowHandler.popup = ColorPickerPopup(
                    { red: Int, green: Int, blue: Int ->

                        OpenRGBManager.updateZoneColor(
                            deviceIndex,
                            button.zoneIndex,
                            String.format("#%02x%02x%02x", red, green, blue)
                        )

                        button.colorHex = String.format("#%02x%02x%02x", red, green, blue)

                        WindowHandler.popup = null
                    }, color.red, color.green, color.blue
                )
            }
        }
    }

    override fun mouseMoved(x: Int, y: Int) {}

    override fun dragMouse(x: Int, y: Int) {}

    override fun mousePressed(x: Int, y: Int) {}

}

class ZoneConfigurationButton(
    val zoneName: String,
    val zoneIndex: Int,
    val x: Int,
    val y: Int,
    var colorHex: String = "#000000"
)