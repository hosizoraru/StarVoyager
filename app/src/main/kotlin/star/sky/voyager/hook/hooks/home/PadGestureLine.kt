package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object PadGestureLine : HookRegister() {
    override fun init() = hasEnable("pad_gesture_line") {
        val navigationBarTypePreferenceFragmentCls =
            loadClass("com.miui.home.recents.settings.NavigationBarTypePreferenceFragment")
        val baseRecentsImplCls =
            loadClass("com.miui.home.recents.BaseRecentsImpl")

        baseRecentsImplCls.methodFinder()
            .filterByName("initHideGestureLine")
            .first().createHook {
                returnConstant(null)
            }

        navigationBarTypePreferenceFragmentCls.methodFinder()
            .filterByName("updatePreferenceVisibility")
            .first().createHook {
                returnConstant(true)
            }
    }
}