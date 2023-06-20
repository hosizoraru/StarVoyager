package star.sky.voyager.hook.hooks.taplus

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.Build.IS_TABLET

object TaplusForPad : HookRegister() {
    override fun init() = hasEnable("unlock_taplus_for_pad") {
        if (!IS_TABLET) return@hasEnable
        loadClass("com.miui.contentextension.setting.activity.MainSettingsActivity").methodFinder()
            .filterByName("getFragment")
            .first().createHook {
                setStaticObject(loadClass("miui.os.Build"), "IS_TABLET", false)
            }
    }
}