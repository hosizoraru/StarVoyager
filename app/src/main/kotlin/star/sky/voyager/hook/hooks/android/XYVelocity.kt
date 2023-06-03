package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister

object XYVelocity : HookRegister() {
    override fun init() {
        val velocityTracker = loadClass("android.view.VelocityTracker")
        velocityTracker.methodFinder()
            .filterByName("getXVelocitygetXVelocity")
            .filterByReturnType(Float::class.java)
            .filterByParamCount(0)
            .first().createHook {
                before {
                    Log.i("X: ${it.result}")
                }
            }
        velocityTracker.methodFinder()
            .filterByName("getXVelocitygetYVelocity")
            .filterByReturnType(Float::class.java)
            .filterByParamCount(0)
            .first().createHook {
                before {
                    Log.i("Y: ${it.result}")
                }
            }
    }
}