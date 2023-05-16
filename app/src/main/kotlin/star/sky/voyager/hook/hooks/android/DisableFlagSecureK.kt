package star.sky.voyager.hook.hooks.android

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Member
import java.lang.reflect.Method
import java.util.function.BiPredicate

class DisableFlagSecureK : IXposedHookLoadPackage {
    private val deoptimizeMethod: Method?

    init {
        var m: Method? = null
        try {
            m = XposedBridge::class.java.getDeclaredMethod("deoptimizeMethod", Member::class.java)
        } catch (t: Throwable) {
            XposedBridge.log(t)
        }
        deoptimizeMethod = m
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    private fun deoptimizeMethod(c: Class<*>, n: String) {
        for (m in c.declaredMethods) {
            if (deoptimizeMethod != null && m.name == n) {
                deoptimizeMethod.invoke(null, m)
                Log.d("DisableFlagSecure", "Deoptimized $m")
            }
        }
    }

    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam) {
        if (loadPackageParam.packageName == "android") {
            try {
                val windowsState = XposedHelpers.findClass(
                    "com.android.server.wm.WindowState",
                    loadPackageParam.classLoader
                )
                XposedHelpers.findAndHookMethod(
                    windowsState,
                    "isSecureLocked",
                    XC_MethodReplacement.returnConstant(false)
                )
            } catch (t: Throwable) {
                XposedBridge.log(t)
            }
            try {
                deoptimizeMethod(
                    XposedHelpers.findClass(
                        "com.android.server.wm.WindowStateAnimator",
                        loadPackageParam.classLoader
                    ),
                    "createSurfaceLocked"
                )
                var c = XposedHelpers.findClass(
                    "com.android.server.display.DisplayManagerService",
                    loadPackageParam.classLoader
                )
                deoptimizeMethod(c, "setUserPreferredModeForDisplayLocked")
                deoptimizeMethod(c, "setUserPreferredDisplayModeInternal")
                c = XposedHelpers.findClass(
                    "com.android.server.wm.InsetsPolicy\$InsetsPolicyAnimationControlListener",
                    loadPackageParam.classLoader
                )
                for (m in c.declaredConstructors) {
                    deoptimizeMethod!!.invoke(null, m)
                }
                c = XposedHelpers.findClass(
                    "com.android.server.wm.InsetsPolicy",
                    loadPackageParam.classLoader
                )
                deoptimizeMethod(c, "startAnimation")
                deoptimizeMethod(c, "controlAnimationUnchecked")
                for (i in 0 until 20) {
                    c = XposedHelpers.findClassIfExists(
                        "com.android.server.wm.DisplayContent$\$ExternalSyntheticLambda",
                        loadPackageParam.classLoader
                    )
                    if (c != null && BiPredicate::class.java.isAssignableFrom(c)) {
                        deoptimizeMethod(c, "test")
                    }
                }
            } catch (t: Throwable) {
                XposedBridge.log(t)
            }
        }
    }
}