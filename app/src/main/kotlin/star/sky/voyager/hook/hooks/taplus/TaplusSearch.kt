package star.sky.voyager.hook.hooks.taplus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.invokeOriginalMethod
import star.sky.voyager.utils.init.HookRegister

object TaplusSearch : HookRegister() {
    override fun init() {
        loadClass("com.miui.contentextension.utils.AppsUtils").methodFinder()
            .filterByName("openGlobalSearch")
            .first().createHook {
                before { param ->
                    val context = param.args[0] as Context
                    val searchString = param.args[1] as String
                    val searchAppPackage = param.args[2] as String

                    if ("com.android.quicksearchbox" == searchAppPackage) {
                        val googleSearchPackage = "com.google.android.googlequicksearchbox"
                        val broadcastReceiver = object : BroadcastReceiver() {
                            override fun onReceive(context: Context, intent: Intent) {
                                setObject(
                                    param.thisObject,
                                    "mCurrentSearchEngine",
                                    googleSearchPackage
                                )
                            }
                        }

                        val filter = IntentFilter("android.search.action.SEARCH_ENGINE_CHANGED")
                        context.registerReceiver(
                            broadcastReceiver, filter,
                            Context.RECEIVER_NOT_EXPORTED
                        )

                        // Start the original global search
                        param.invokeOriginalMethod()

                        // Unregister the broadcast receiver
                        context.unregisterReceiver(broadcastReceiver)
                    }
                }
            }
    }
}