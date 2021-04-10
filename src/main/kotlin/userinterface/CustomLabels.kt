package userinterface

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.nio.file.FileSystems

object CustomLabels {

    /**
     * [File] where the custom labels are saved in
     */
    val file = File("files${FileSystems.getDefault().separator}labels.json")

    fun createFile() {
        if (!file.exists()) {
            file.writeText("{}")
        }
    }

    fun getLabel(deviceName: String, zoneIndex: Int): String {
        val json = JSONParser().parse(file.readText()) as JSONObject

        return if (json.containsKey(deviceName)) {
            val jsonObject = json[deviceName]!! as JSONObject

            if (jsonObject.containsKey(zoneIndex.toString())) {
                jsonObject[zoneIndex.toString()] as String
            } else {
                ""
            }
        } else {
            ""
        }
    }

    fun saveLabel(deviceName: String, zoneIndex: Int, customName: String) {
        val json = JSONParser().parse(file.readText()) as JSONObject

        if (json.containsKey(deviceName)) {
            val jsonObject = json[deviceName] as JSONObject
            jsonObject[zoneIndex.toString()] = customName
            json[deviceName] = jsonObject
        } else {
            val jsonObject = JSONObject()
            jsonObject[zoneIndex.toString()] = customName
            json[deviceName] = jsonObject
        }

        file.writeText(json.toJSONString())
    }

}