package userinterface

import userinterface.screens.DevicesScreen
import userinterface.screens.HelpScreen
import userinterface.screens.UpdatesScreen

enum class SidebarItem(
    val displayName: String,
    val imageName: String,
    val screen: Screen,
    val index: Int,
    val isAvailable: Boolean = true
) {

    DEVICES("Devices", "devices", DevicesScreen(), 0, isAvailable = true),
    UPDATES("Updates", "updates", UpdatesScreen(), 1, isAvailable = false),
    HELP("Help", "help", HelpScreen(), 2, isAvailable = true)

}