package openrgb

import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import utils.Logger
import java.lang.Exception
import java.net.ConnectException

object OpenRGBManager {

    lateinit var client: OpenRGBClient
    var connected: Boolean = false

    var devices = HashMap<String, Int>()
    var reveredDevices = HashMap<Int, String>()

    fun connect() {
        Logger.debug("Trying to connect to OpenRGB...")

        try {
            client = OpenRGBClient("localhost", 6742, "BetterRGB | v${BetterRGB.version} - ${BetterRGB.buildDate}")

            client.connect()
            connected = true

            Logger.info("Successfully connected to OpenRGB!")
        } catch (e: ConnectException) {
            connected = false
            Logger.warn("Failed to connect to OpenRGB!")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        updateDevices()
    }

    fun updateDevices() {
        Logger.debug("Updating OpenRGB devices...")

        if(connected) {
            for(index in 0 until client.controllerCount) {
                val device = client.getDeviceController(index)
                var name = device.name
                name = name.substring(0, name.length - 1)

                while (true) {
                    if(name.endsWith(" ")) {
                        name = name.substring(0, name.length - 1)
                    }else {
                        break
                    }
                }

                devices[name] = index
                reveredDevices[index] = name
            }

            Logger.info("Successfully updated OpenRGB devices!")
        }else {
            Logger.warn("Skipped device updating, because client is not connected yet")
        }
    }

    fun getDeviceZoneNames(deviceIndex: Int): HashMap<String, Int> {
        val device = client.getDeviceController(deviceIndex)
        val result = HashMap<String, Int>()

        var index = 0
        for(zone in device.zones) {
            var name = zone.name
            name = name.substring(0, name.length - 1)

            result[name] = index
            index++
        }

        return result
    }

}