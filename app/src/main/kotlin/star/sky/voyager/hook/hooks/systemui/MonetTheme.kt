package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils
import star.sky.voyager.utils.key.hasEnable

object MonetTheme : HookRegister() {
    override fun init() = hasEnable("monet_theme") {
        loadClassOrNull("com.android.systemui.theme.ThemeOverlayController")?.methodFinder()
            ?.first {
                name == "getOverlay" && parameterCount == 3 && parameterTypes[0] == Int::class.java && parameterTypes[1] == Int::class.java
            }?.createHook {
                before { param ->
                    param.args[0] =
                        Color.parseColor(XSPUtils.getString("your_theme_accent_color", "#0d84ff"))
                    Log.i("Your monet theme color1:" + param.args[0])
                    param.args[1] =
                        Color.parseColor(XSPUtils.getString("your_theme_neutral_color", "#0d84ff"))
                    Log.i("Your monet theme color2:" + param.args[1])
//                    param.args[2] = XSPUtils.getString("your_theme_style", "TONAL_SPOT")
                    Log.i("Your monet theme style:" + param.args[2])
                }
            }
    }
}
