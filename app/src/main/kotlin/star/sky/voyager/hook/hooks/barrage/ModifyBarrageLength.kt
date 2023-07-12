package star.sky.voyager.hook.hooks.barrage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object ModifyBarrageLength : HookRegister() {
    override fun init() = hasEnable("modify_barrage_length") {
        val barrageLength = getInt("barrage_length", 36)
        if (barrageLength == 36) return@hasEnable
        val clazzString = loadClass("java.lang.String")

        clazzString.methodFinder()
            .filterByName("subSequence")
            .filterByParamCount(2)
            .first().createHook {
                before { param ->
                    val stacktrace = Throwable().stackTrace
                    if (stacktrace.any { it.className == "com.xiaomi.barrage.utils.BarrageWindowUtils" }) {
                        param.args[1] = barrageLength
                    }
                }
            }

        clazzString.methodFinder()
            .filterByName("length")
            .filterByParamCount(0)
            .first().createHook {
                after { param ->
                    val stacktrace = Throwable().stackTrace
                    if (stacktrace.any { it.className == "java.lang.String" }) return@after
                    if (stacktrace.firstOrNull { it.className == "com.xiaomi.barrage.utils.BarrageWindowUtils" }
                            ?.methodName in setOf("addBarrageNotification", "sendBarrage")) {
                        val result = (param.result as Int)
                        param.result = if (barrageLength < 36) {
                            if (result > barrageLength) {
                                maxOf(37, result)
                            } else result
                        } else {
                            if (result < barrageLength) {
                                minOf(35, result)
                            } else result
                        }
                    }
                }
            }
    }
}