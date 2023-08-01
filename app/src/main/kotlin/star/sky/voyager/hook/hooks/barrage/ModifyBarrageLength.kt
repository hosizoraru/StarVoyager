package star.sky.voyager.hook.hooks.barrage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object ModifyBarrageLength : HookRegister() {
    private val barrageLength by lazy {
        getInt("barrage_length", 36)
    }

    override fun init() = hasEnable("modify_barrage_length") {
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
                    if (stacktrace.any {
                            it.className in setOf(
                                "java.lang.String",
                                "android.text.SpannableStringBuilder"
                            )
                        }) return@after
                    if (stacktrace.any {
                            it.className == "com.xiaomi.barrage.utils.BarrageWindowUtils" && it.methodName in setOf(
                                "addBarrageNotification", "sendBarrage"
                            )
                        }) {
                        val realResult = (param.result as Int)
                        param.result = if (barrageLength < 36) {
                            if (realResult > barrageLength) {
                                maxOf(37, realResult)
                            } else realResult
                        } else {
                            if (realResult <= barrageLength) {
                                minOf(35, realResult)
                            } else realResult
                        }
                    }
                }
            }
    }
}