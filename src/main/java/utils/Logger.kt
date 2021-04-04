package utils

import java.text.SimpleDateFormat
import java.util.*

object Logger {

    private var formattedTime = SimpleDateFormat("HH:mm:ss").format(Date())

    private val reset = "\u001B[0m"
    private val white = "\u001B[37m"
    private val black = "\u001B[30m"
    private val grey = "\u001B[90m"

    private val cyan = "\u001B[36m"
    private val red = "\u001B[31m"
    private val orange = "\u001B[33m"

    fun info(msg: String) {
        println("$grey[${formattedTime}]: [${cyan}INFO$grey] $reset$msg")
    }

    fun warn(msg: String) {
        println("$grey[${formattedTime}]: [${orange}WARN$grey] $reset$msg")
    }

    fun error(msg: String) {
        println("$grey[${formattedTime}]: [${red}ERROR$grey] $red$msg")
    }


}