package star.sky.voyager.hook.hooks.securitycenter

import android.graphics.Color
import com.github.kyuubiran.ezxhelper.Log
import star.sky.voyager.utils.init.ResourcesHookRegister

object SidebarLineColor : ResourcesHookRegister() {
    override fun init() {
        val qwq = Color.parseColor("#0d84ff")
        val sidebarColorId = getInitPackageResourcesParam().res.getIdentifier(
            "sidebar_line_color_light",
            "color",
            "com.miui.securitycenter"
        )
        Log.i("colorId: $sidebarColorId")
        var sidebarColor = getInitPackageResourcesParam().res.getColor(sidebarColorId)
        Log.i("color1: $sidebarColor")
        getInitPackageResourcesParam().res.setReplacement(
            "com.miui.securitycenter",
            "color",
            "sidebar_guide_text_color",
            0x800000FF
        )
        getInitPackageResourcesParam().res.setReplacement(sidebarColorId, qwq)
        sidebarColor = getInitPackageResourcesParam().res.getColor(sidebarColorId)
        Log.i("color2: $sidebarColor")
    }
}