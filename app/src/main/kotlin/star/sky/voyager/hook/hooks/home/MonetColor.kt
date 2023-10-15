package star.sky.voyager.hook.hooks.home

import android.annotation.SuppressLint
import android.content.res.Resources.getSystem
import android.graphics.Color.parseColor
import star.sky.voyager.utils.init.ResourcesHookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object MonetColor : ResourcesHookRegister() {
    @SuppressLint("DiscouragedApi")
    override fun init() = hasEnable("monet_color") {
        val monet = "system_accent1_100"
        val monoColorId = getSystem().getIdentifier(monet, "color", "android")
        var monoColor = getSystem().getColor(monoColorId)
        hasEnable("use_edit_color") {
            monoColor = parseColor(getString("your_color", "#0d84ff"))
        }
        getInitPackageResourcesParam().res.setReplacement(
            "com.miui.home",
            "color",
            "monochrome_default",
            monoColor
        )
    }
}