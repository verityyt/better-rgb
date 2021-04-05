package userinterface.listener

import userinterface.WindowHandler
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseListener : MouseListener {

    override fun mouseClicked(e: MouseEvent) {
        WindowHandler.screen?.mouseClicked(e.x - 5, e.y - 25)
        WindowHandler.popup?.mouseClicked(e.x - 5, e.y - 25)
    }

    override fun mousePressed(e: MouseEvent) {
        WindowHandler.screen?.mousePressed(e.x - 5, e.y - 25)
        WindowHandler.popup?.mousePressed(e.x - 5, e.y - 25)
    }

    override fun mouseReleased(e: MouseEvent) { }

    override fun mouseEntered(e: MouseEvent) { }

    override fun mouseExited(e: MouseEvent) { }

}