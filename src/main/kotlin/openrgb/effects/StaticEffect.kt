package openrgb.effects

import openrgb.Effect

class StaticEffect(private val primaryColorHex: String) : Effect("Static") {

    override var animation = false

    override var thread = Thread {
        colorHex = primaryColorHex
    }

}