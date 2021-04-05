package userinterface.listener

import userinterface.WindowHandler
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

class MouseMotionListener : MouseMotionListener {

    override fun mouseDragged(e: MouseEvent) {
        WindowHandler.screen?.dragMouse(e.x - 5, e.y - 25)
        WindowHandler.popup?.dragMouse(e.x - 5, e.y - 25)
    }

    override fun mouseMoved(e: MouseEvent) {
        WindowHandler.screen?.mouseMoved(e.x - 5, e.y - 25)
        WindowHandler.popup?.mouseMoved(e.x - 5, e.y - 25)
    }

}