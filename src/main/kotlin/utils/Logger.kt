package utils

import java.text.SimpleDateFormat
import java.util.*

object Logger {

    private val enableDebug = true
    private val enableTrace = true
    private val enableInterface = true

    private fun getFormattedTime() =  SimpleDateFormat("HH:mm:ss").format(Date())

    private const val reset = "\u001B[0m"
    private const val grey = "\u001B[90m"

    private const val cyan = "\u001B[36m"
    private const val red = "\u001B[31m"
    private const val orange = "\u001B[33m"
    private const val green = "\u001B[32m"
    private const val purple = "\u001B[35m"
    private const val yellow = "\u001B[33m"

    fun info(msg: String) {
        println("$grey[${getFormattedTime()}]: [${green}INFO$grey] $reset$msg")
    }

    fun warn(msg: String) {
        println("$grey[${getFormattedTime()}]: [${orange}WARN$grey] $reset$msg")
    }

    fun error(msg: String) {
        println("$grey[${getFormattedTime()}]: [${red}ERROR$grey] $red$msg")
    }

    fun debug(msg: String) {
        if(enableDebug) {
            println("$grey[${getFormattedTime()}]: [${cyan}DEBUG$grey] $reset$msg")
        }
    }

    fun trace(msg: String) {
        if(enableTrace) {
            println("$grey[${getFormattedTime()}]: [${purple}TRACE$grey] $reset$msg")
        }
    }

    fun `interface`(msg: String) {
        if(enableInterface) {
            println("$grey[${getFormattedTime()}]: [${yellow}INTERFACE$grey] $reset$msg")
        }
    }

}