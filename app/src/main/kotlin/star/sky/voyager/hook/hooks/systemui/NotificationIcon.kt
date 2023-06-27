package star.sky.voyager.hook.hooks.systemui

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable


object NotificationIcon : HookRegister() {
    override fun init() = hasEnable("notification_icon") {
        val notificationClass =
            loadClass("com.android.systemui.qs.MiuiNotificationHeaderView")
        val qsNotificationCls =
            loadClass("com.android.systemui.qs.MiuiQSHeaderView")

        setOf(notificationClass, qsNotificationCls).forEach {
            it.methodFinder()
                .filterByName("updateShortCutVisibility")
                .toList().createHooks {
                    before { param ->
                        val mShortCut =
                            getObjectOrNullAs<View>(param.thisObject, "mShortCut")!!
                        mShortCut.visibility = View.GONE
                        param.result = null
                    }
                }
        }
    }
}