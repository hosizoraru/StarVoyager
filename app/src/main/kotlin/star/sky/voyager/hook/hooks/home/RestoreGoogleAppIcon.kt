package star.sky.voyager.hook.hooks.home

import android.content.ComponentName
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RestoreGoogleAppIcon : HookRegister() {
    override fun init() = hasEnable("restore_google_app_icon") {
        loadClass("com.miui.home.launcher.AppFilter").constructors.createHooks {
            after { param ->
                param.thisObject.objectHelper {
                    getObjectOrNullAs<HashSet<ComponentName>>("mSkippedItems")?.removeIf {
                        it.packageName in listOf(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.gms"
                        )
                    }
                }
            }
        }
    }
}