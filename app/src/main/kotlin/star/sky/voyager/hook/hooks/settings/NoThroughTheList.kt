package star.sky.voyager.hook.hooks.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NoThroughTheList : HookRegister() {
    override fun init() = hasEnable("no_through_the_list") {
        loadClass("com.android.settings.SettingsActivity").methodFinder()
            .filterByName("onCreate")
            .filterByParamTypes(Bundle::class.java)
            .first().createHook {
                before {

                }
                after { param ->
                    val thisObject = param.thisObject
                    val context = thisObject as Context
                    val getIntentMethod = thisObject.javaClass.getMethod("getIntent")
                    val intent = getIntentMethod.invoke(thisObject) as Intent
                    if (intent.action == "android.settings.action.MANAGE_OVERLAY_PERMISSION") {
                        val packageName = intent.data.toString().substring(8)
                        @SuppressLint("PrivateApi") val intentOpenSub = Intent(
                            context,
                            classLoader.loadClass("com.android.settings.SubSettings")
                        )
                        intentOpenSub.setAction("android.intent.action.MAIN")
                        intentOpenSub.putExtra(":settings:source_metrics", 221)
                        intentOpenSub.putExtra(
                            ":settings:show_fragment",
                            "com.android.settings.applications.appinfo.DrawOverlayDetails"
                        )
                        val bundle1 = Bundle()
                        bundle1.putString("package", packageName)
                        intentOpenSub.putExtra(":settings:show_fragment_args", bundle1)
                        context.startActivity(intentOpenSub)
                    }
                }
            }
    }
}