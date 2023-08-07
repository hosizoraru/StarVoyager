package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object FakeNavBar : HookRegister() {
    override fun init() = hasEnable("fake_nav_bar") {
        loadClass("com.miui.home.recents.views.RecentsContainer").methodFinder()
            .filterByName("hideFakeNavBarForHidingGestureLine")
            .first().createHook {
                before {
                    it.args[0] = true
                }
            }
    }
}

//        loadClass("com.miui.home.recents.views.RecentsContainer").methodFinder()
//            .filterByName("showLandscapeOverviewGestureView")
//            .filterByParamTypes(Boolean::class.java)
//            .first().createHook {
//                returnConstant(null)
//            }