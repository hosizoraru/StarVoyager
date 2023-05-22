package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object MonetTheme : HookRegister() {
    override fun init() = hasEnable("monet_theme") {
        loadClassOrNull("com.android.systemui.theme.ThemeOverlayController")?.methodFinder()
            ?.first {
                name == "getOverlay" && parameterCount == 3 && parameterTypes[0] == Int::class.java && parameterTypes[1] == Int::class.java
            }?.createHook {
                before { param ->
                    Log.i("Monet Key: " + param.args[1] as Int)
                    when (param.args[1] as Int) {
                        1 -> {
                            // accent color
                            Log.i("Monet Key: " + (param.args[1] as Int).toString(16))
                            Log.i(
                                "System accent color: " + Integer.toUnsignedLong(param.args[0] as Int)
                                    .toString(16)
                            )
                            param.args[0] =
                                Color.parseColor(getString("your_theme_accent_color", "#0d84ff"))
                            Log.i(
                                "Your accent color: " + Integer.toUnsignedLong(param.args[0] as Int)
                                    .toString(16)
                            )
                        }
                        // else 0
                        0 -> {
                            // neutral color
                            Log.i("Monet Key: " + (param.args[1] as Int).toString(16))
                            Log.i(
                                "System neutral color: " + Integer.toUnsignedLong(param.args[0] as Int)
                                    .toString(16)
                            )
                            param.args[0] =
                                Color.parseColor(getString("your_theme_neutral_color", "#0d84ff"))
                            Log.i(
                                "System neutral color: " + Integer.toUnsignedLong(param.args[0] as Int)
                                    .toString(16)
                            )
                        }
                    }
//                    param.args[1] =
//                        Color.parseColor(XSPUtils.getString("your_theme_neutral_color", "#0d84ff"))
//                    Log.i("System monet theme color2:" + (param.args[1] as Int).toString(16))
//                    Log.i("Your monet theme color2:" + (param.args[1] as Int).toString(16))
//                    Log.i("System monet theme style:" + param.args[2])
//                    param.args[2] = XSPUtils.getString("your_theme_style", "TONAL_SPOT")
//                    Log.i("Your monet theme style:" + param.args[2])
                }
            }
    }
}
