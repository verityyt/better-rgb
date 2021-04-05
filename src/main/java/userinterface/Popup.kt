package userinterface

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.ImageObserver

abstract class Popup {

    abstract fun paint(g: Graphics, g2: Graphics2D, observer: ImageObserver)

    abstract fun mouseClicked(x: Int, y: Int)

    abstract fun mouseMoved(x: Int, y: Int)

    abstract fun dragMouse(x: Int, y: Int)

    abstract fun mousePressed(x: Int, y: Int)

}