package star.sky.voyager.hook.hooks.home

import android.content.ComponentName
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RestoreGoogleAppIcon : HookRegister() {
    override fun init() = hasEnable("restore_google_app_icon") {
        loadClass("com.miui.home.launcher.AppFilter").declaredConstructors.createHooks {
            after { param ->
                getObjectOrNullAs<HashSet<ComponentName>>(
                    param.thisObject,
                    "mSkippedItems"
                )!!.removeIf {
                    it.packageName in setOf(
                        "com.google.android.googlequicksearchbox",
                        "com.google.android.gms"
                    )
                }
            }
        }
    }
}