package openrgb

import io.gitlab.mguimard.openrgb.client.OpenRGBClient
import io.gitlab.mguimard.openrgb.entity.OpenRGBColor
import io.gitlab.mguimard.openrgb.entity.OpenRGBZone
import userinterface.screens.DeviceZoneScreen
import utils.Logger
import java.lang.Exception
import java.net.ConnectException

object OpenRGBManager {

    private lateinit var client: OpenRGBClient

    /**
     * Whether the [client] is connected or not
     */
    var connected: Boolean = false

    /**
     * [client]s devices sorted by name
     *
     * **Key**: DeviceName
     *
     * **Value**: DeviceIndex
     */
    var deviceByName = HashMap<String, Int>()

    /**
     * [client]s devices sorted by index
     *
     * **Key**: DeviceIndex
     *
     * **Value**: DeviceName
     */
    var deviceByIndex = HashMap<Int, String>()

    /**
     * [DeviceZoneScreen]s sorted by the DeviceIndex
     */
    var deviceZoneScreens = HashMap<Int, DeviceZoneScreen>()

    /**
     * Zone effects [thread][Effect.thread] sorted by DeviceIndex
     *
     * **Key**: DeviceIndex
     *
     * **Value**: HashMap
     * > **Key**: ZoneIndex
     *
     * > **Value**:  Zone effects [thread][Effect.thread]
     */
    private var deviceEffects = HashMap<Int, HashMap<Int, Thread>>()

    /**
     * Zone led count sorted by Device- and ZoneIndex
     *
     * **Key**: DeviceIndex
     *
     * **Value**: HashMap
     * > **Key**: ZoneIndex
     *
     * > **Value**:  Led count of zone
     */
    private var deviceZoneLedCount = HashMap<Int, HashMap<Int, Int>>()

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

    /**
     * Fetches devices from OpenRGB and save the data in variables
     *
     * > [deviceByIndex], [deviceByName], [deviceZoneScreens]
     */
    fun updateDevices() {
        Logger.debug("Updating OpenRGB devices...")

        if (connected) {
            for (index in 0 until client.controllerCount) { // Looping through all devices of OpenRGB
                val device = client.getDeviceController(index)
                var name = device.name
                name = name.substring(0, name.length - 1) // Remove bugged character at the end of the DeviceName

                while (true) { // Removes every spaces at the end of the DeviceName
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

    /**
     * Fetches zone names from device by DeviceIndex
     * @return HashMap
     *
     * > **Key**: ZoneIndex
     *
     * > **Value**: ZoneName
     */
    fun getDeviceZones(deviceIndex: Int): HashMap<Int, String> {
        Logger.debug("Fetching zones of '${deviceByIndex[deviceIndex]}#$deviceIndex'...")

        val device = client.getDeviceController(deviceIndex)
        val result = HashMap<Int, String>()

        var index = 0
        for (zone in device.zones) { // Looping through every zone of the device by DeviceIndex
            var name = zone.name
            name = name.substring(0, name.length - 1)

            cacheZoneLedCount(deviceIndex, index, zone)

            result[index] = name
            index++
        }

        Logger.info("Successfully fetched zones of '${deviceByIndex[0]}#$deviceIndex'!")

        return result
    }

    fun cacheZoneLedCount(deviceIndex: Int, zoneIndex: Int, zone: OpenRGBZone) {

        if (deviceZoneLedCount.containsKey(deviceIndex)) {
            val map = deviceZoneLedCount[deviceIndex]!!
            if (!map.containsKey(zoneIndex)) {
                Logger.debug("Caching led count of ${zone.name} zone of ${deviceByIndex[deviceIndex]}...")

                map[zoneIndex] = zone.ledsCount
                deviceZoneLedCount[deviceIndex] = map

                Logger.info("Successfully cached led count of ${zone.name} zone of ${deviceByIndex[deviceIndex]}!")
            }
        } else {
            Logger.debug("Caching led count of ${zone.name} zone of ${deviceByIndex[deviceIndex]}...")

            val map = HashMap<Int, Int>()
            map[zoneIndex] = zone.ledsCount
            deviceZoneLedCount[deviceIndex] = map

            Logger.info("Successfully cached led count of ${zone.name} zone of ${deviceByIndex[deviceIndex]}!")
        }
    }

    /**
     * Updates color/effect of zone by DeviceIndex and ZoneIndex
     */
    fun updateZoneColor(deviceIndex: Int, zoneIndex: Int, effect: Effect) {

        if (deviceEffects.containsKey(deviceIndex)) {
            val zoneThreads = deviceEffects[deviceIndex] as HashMap<Int, Thread>

            if (zoneThreads.containsKey(zoneIndex)) { // Check if a effect is already running on the zone by DeviceIndex and ZoneIndex
                zoneThreads[zoneIndex]?.stop() // Stopping current running effect of the zone
                zoneThreads.remove(zoneIndex)
            }

            deviceEffects[deviceIndex] = zoneThreads
        }

        if (effect.animation) { // Check if new effect is a animation or not *(static)*
            val thread = Thread { // Setting up thread to set current effects color
                effect.start()

                while (true) {
                    Thread.sleep((1000 / effect.fps).toLong()) // Sleeping to make effects fps working

                    // Sending color to rgb controller

                    val colors =
                        arrayOfNulls<OpenRGBColor>(client.getDeviceController(deviceIndex).zones[zoneIndex].ledsCount)
                    colors.fill(OpenRGBColor.fromHexaString(effect.colorHex))

                    client.updateZoneLeds(deviceIndex, zoneIndex, colors)
                }
            }

            // Saving new effect in variables (deviceEffects)

            if (deviceEffects.containsKey(deviceIndex)) {
                val zoneThreads = deviceEffects[deviceIndex] as HashMap<Int, Thread>
                zoneThreads[zoneIndex] = thread

                deviceEffects[deviceIndex] = zoneThreads
            } else {
                val zoneThreads = HashMap<Int, Thread>()
                zoneThreads[zoneIndex] = thread

                deviceEffects[deviceIndex] = zoneThreads
            }

            thread.start() // Starting thread -> effect is running now
        } else {
            effect.start() // Starting effects thread (setting static color)
            effect.join() // Waiting for thread (doesn't need to run parallel for/on static effect)

            // Sending color to rgb controller

            val colors = arrayOfNulls<OpenRGBColor>(client.getDeviceController(deviceIndex).zones[zoneIndex].ledsCount)
            colors.fill(OpenRGBColor.fromHexaString(effect.colorHex))

            client.updateZoneLeds(deviceIndex, zoneIndex, colors)
        }
    }

}