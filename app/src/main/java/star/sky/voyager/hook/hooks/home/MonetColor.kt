package star.sky.voyager.hook.hooks.home

import android.content.res.Resources
import android.graphics.Color
import star.sky.voyager.utils.init.ResourcesHookRegister
import star.sky.voyager.utils.key.XSPUtils
import star.sky.voyager.utils.key.hasEnable

object MonetColor : ResourcesHookRegister() {
    override fun init() = hasEnable("monoet_color") {
        val monet = "system_accent1_100"
        val monoColorId = Resources.getSystem().getIdentifier(monet, "color", "android")
        var monoColor = Resources.getSystem().getColor(monoColorId)
        hasEnable("use_edit_color") {
            monoColor = Color.parseColor(XSPUtils.getString("your_color","#0d84ff"))
        }
        getInitPackageResourcesParam().res.setReplacement(
            "com.miui.home",
            "color",
            "monochrome_default",
            monoColor
        )
    }
}