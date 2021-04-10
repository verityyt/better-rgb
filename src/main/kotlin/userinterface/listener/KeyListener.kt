package userinterface.listener

import userinterface.WindowHandler
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyListener : KeyListener {

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {}

    override fun keyReleased(e: KeyEvent) {
        WindowHandler.popup?.keyReleased(e.keyChar, e.keyCode)
    }

}