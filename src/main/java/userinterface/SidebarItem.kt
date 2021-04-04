package userinterface

import userinterface.screens.HelpScreen

enum class SidebarItem(
    val displayName: String,
    val imageName: String,
    val screen: Screen,
    val index: Int,
    val isAvailable: Boolean = true,
    val isActive: Boolean = false
) {

    DEVICES("Devices", "devices", HelpScreen(), 0, isAvailable = true),
    UPDATES("Updates", "updates", HelpScreen(), 1, isAvailable = false),
    HELP("Help", "help", HelpScreen(), 2, isAvailable = true)

}