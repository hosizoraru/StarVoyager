package star.sky.voyager.hook.hooks.barrage

import android.app.Notification
import android.service.notification.StatusBarNotification
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AnyAppBarrage : HookRegister() {
    override fun init() = hasEnable("any_app_barrage") {
        loadClass("com.xiaomi.barrage.service.NotificationMonitorService").methodFinder()
            .filterByName("filterNotification")
            .first().createHook {
                before { param ->
                    val sbn = param.args[0] as StatusBarNotification
                    val packageName = sbn.packageName
                    getObjectOrNullAs<ArrayList<String>>(
                        param.thisObject, "mBarragePackageList"
                    )!!.apply {
                        if (!contains(packageName)) add(packageName)
                    }
                    if (shouldExclude(sbn)) param.result = true
                }
            }
    }

    private fun shouldExclude(sbn: StatusBarNotification): Boolean {
        // 这里检查通知是否是常驻的
        val isOngoing =
            sbn.notification.flags and Notification.FLAG_ONGOING_EVENT != 0

        // 检查通知是否是前台服务通知
        val isForegroundService =
            sbn.notification.flags and Notification.FLAG_FOREGROUND_SERVICE != 0

        // 或者你可以添加其他的检查，例如检查通知是否来自系统应用：
        // val isSystemApp = (sbn.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

        return isOngoing || isForegroundService // || isSystemApp 或者其他你想要的条件
    }
}