package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

@SuppressLint("UnspecifiedRegisterReceiverFlag")
object HideStatusBarWhenScreenShot : HookRegister() {
    override fun init() = hasEnable("hide_status_bar_when_screenshot") {
        loadClass("com.android.systemui.statusbar.phone.MiuiCollapsedStatusBarFragment")
            .methodFinder().filterByName("initMiuiViewsOnViewCreated")
            .first().createHook {
                after { param ->
                    val view = param.args[0] as View
                    val br: BroadcastReceiver = object : BroadcastReceiver() {
                        override fun onReceive(context: Context, intent: Intent) {
                            if ("miui.intent.TAKE_SCREENSHOT" == intent.action) {
                                val finished = intent.getBooleanExtra("IsFinished", true)
                                view.visibility = if (finished) View.VISIBLE else View.INVISIBLE
                            }
                        }
                    }
                    view.context.registerReceiver(
                        br, IntentFilter("miui.intent.TAKE_SCREENSHOT"),
                    )
                }
            }
    }
}