package star.sky.voyager.hook.hooks.home

import android.content.Context
import android.content.Intent
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedBridge.invokeOriginalMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RestoreGoogleSearch : HookRegister() {
    override fun init() = hasEnable("restore_google_search") {
        val searchBarDesktopLayout =
            loadClass("com.miui.home.launcher.SearchBarDesktopLayout")

        searchBarDesktopLayout.methodFinder()
            .filterByName("launchGlobalSearch")
            .filterByParamTypes(String::class.java, String::class.java)
            .first().createHook {
                replace { param ->
                    val context = getObjectOrNullAs<Context>(param.thisObject, "mLauncher")!!

                    try {
                        context.startActivity(
                            Intent("android.search.action.GLOBAL_SEARCH").apply {
                                addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK
                                            // and Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                                )
                                setPackage("com.google.android.googlequicksearchbox")
                            }
                        )
                    } catch (e: Exception) {
                        // fallback doesn't work, still keep the code here because I don't care
                        invokeOriginalMethod(param.method, param.thisObject, param.args)
                    }
                }
            }
    }
}
