package star.sky.voyager.hook.hooks.home

import android.view.WindowManager
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable
import kotlin.math.roundToInt


object BackGestureAreaHeight : HookRegister() {
    override fun init() = hasEnable("back_gesture_area_height") {
        loadClass("com.miui.home.recents.GestureStubView").methodFinder()
            .filterByName("getGestureStubWindowParam")
            .first().createHook {
                after { param ->
                    val wmlp = param.result as WindowManager.LayoutParams
                    val wmlpHeight = getInt("wmlp_height", 60)
                    wmlp.height = (wmlp.height / 60.0F * wmlpHeight).roundToInt()
                    param.result = wmlp
                }
            }
    }
}