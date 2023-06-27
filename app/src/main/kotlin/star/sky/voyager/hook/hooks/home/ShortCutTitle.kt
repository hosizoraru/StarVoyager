package star.sky.voyager.hook.hooks.home

import star.sky.voyager.utils.init.ResourcesHookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object ShortCutTitle : ResourcesHookRegister() {
    override fun init() = hasEnable("shortcut_title_custom") {
        replaceHomeString(
            "system_shortcuts_more_operation",
            getString("app_info", "App Info")!!
        )
        replaceHomeString(
            "share",
            getString("share", "Share")!!
        )
        replaceHomeString(
            "system_shortcuts_remove",
            getString("remove", "Remove")!!
        )
        replaceHomeString(
            "edit_mode_uninstall",
            getString("uninstall", "Uninstall")!!
        )
    }

    private fun replaceHomeString(key: String, replacement: String) {
        getInitPackageResourcesParam().res.setReplacement(
            "com.miui.home",
            "string",
            key,
            replacement
        )
    }
}