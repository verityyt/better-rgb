package openrgb

import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import userinterface.screens.DeviceZoneScreen
import utils.Logger
import java.lang.Exception
import java.net.ConnectException

object OpenRGBManager {

    lateinit var client: OpenRGBClient
    var connected: Boolean = false

    var deviceByName = HashMap<String, Int>()
    var deviceByIndex = HashMap<Int, String>()
    var deviceZoneScreens = HashMap<Int, DeviceZoneScreen>()

    var deviceEffects = HashMap<Int, HashMap<Int, Thread>>()

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

        if (connected) {
            for (index in 0 until client.controllerCount) {
                val device = client.getDeviceController(index)
                var name = device.name
                name = name.substring(0, name.length - 1)

                while (true) {
                    if (name.endsWith(" ")) {
                        name = name.substring(0, name.length - 1)
                    } else {
                        break
                    }
                }

                deviceByName[name] = index
                deviceByIndex[index] = name

                deviceZoneScreens[index] = DeviceZoneScreen(name, index)
            }

            Logger.info("Successfully updated OpenRGB devices!")
        } else {
            Logger.warn("Skipped device updating, because client is not connected yet")
        }
    }

    fun getDeviceZoneNames(deviceIndex: Int): HashMap<Int, String> {
        Logger.debug("Fetching zones of '${deviceByIndex[deviceIndex]}#$deviceIndex'...")

        val device = client.getDeviceController(deviceIndex)
        val result = HashMap<Int, String>()

        var index = 0
        for (zone in device.zones) {
            var name = zone.name
            name = name.substring(0, name.length - 1)

            result[index] = name
            index++
        }

        Logger.info("Successfully fetched zones of '${deviceByIndex[0]}#$deviceIndex'!")

        return result
    }

    fun updateZoneColor(deviceIndex: Int, zoneIndex: Int, effect: Effect) {

        if (deviceEffects.containsKey(deviceIndex)) {
            val zoneThreads = deviceEffects[deviceIndex] as HashMap<Int, Thread>

            if (zoneThreads.containsKey(zoneIndex)) {
                zoneThreads[zoneIndex]?.stop()
                zoneThreads.remove(zoneIndex)
            }

            deviceEffects[deviceIndex] = zoneThreads
        }

        if (effect.animation) {
            val thread = Thread {
                effect.start()

                while (true) {
                    Thread.sleep((1000 / effect.fps).toLong())

                    val colors =
                        arrayOfNulls<OpenRGBColor>(client.getDeviceController(deviceIndex).zones[zoneIndex].ledsCount)
                    colors.fill(OpenRGBColor.fromHexaString(effect.colorHex))

                    client.updateZoneLeds(deviceIndex, zoneIndex, colors)
                }
            }

            if (deviceEffects.containsKey(deviceIndex)) {
                val zoneThreads = deviceEffects[deviceIndex] as HashMap<Int, Thread>
                zoneThreads[zoneIndex] = thread

                deviceEffects[deviceIndex] = zoneThreads
            } else {
                val zoneThreads = HashMap<Int, Thread>()
                zoneThreads[zoneIndex] = thread

                deviceEffects[deviceIndex] = zoneThreads
            }

            thread.start()
        } else {

            effect.start()
            effect.join()
            effect.colorHex

            val colors = arrayOfNulls<OpenRGBColor>(client.getDeviceController(deviceIndex).zones[zoneIndex].ledsCount)
            colors.fill(OpenRGBColor.fromHexaString(effect.colorHex))

            client.updateZoneLeds(deviceIndex, zoneIndex, colors)
        }
    }

}