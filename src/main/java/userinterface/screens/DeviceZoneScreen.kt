package userinterface.screens

import openrgb.OpenRGBManager
import userinterface.Screen
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

class DeviceZoneScreen(val deviceName: String, val deviceIndex: Int) : Screen() {

    private var deviceZones: HashMap<String, Int> = OpenRGBManager.getDeviceZoneNames(deviceIndex)

    init {
        println(deviceZones)

        /*OpenRGBManager.updateZoneColor(deviceIndex, 2, "#ff00ea")*/
    }

    override fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver) {


    }

    override fun mouseClicked(x: Int, y: Int) {}

    override fun mouseMoved(x: Int, y: Int) {}

}