package star.sky.voyager.hook.hooks.android

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.XposedHelpers.findClassIfExists
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
            Log.e("DisableFlagSecure", "Failed to get deoptimizeMethod", t)
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
                val windowsState = findClass(
                    "com.android.server.wm.WindowState",
                    loadPackageParam.classLoader
                )
                findAndHookMethod(
                    windowsState,
                    "isSecureLocked",
                    XC_MethodReplacement.returnConstant(false)
                )
            } catch (t: Throwable) {
                Log.e("DisableFlagSecure", "Failed to hook isSecureLocked", t)
            }
            try {
                deoptimizeMethod(
                    findClass(
                        "com.android.server.wm.WindowStateAnimator",
                        loadPackageParam.classLoader
                    ),
                    "createSurfaceLocked"
                )
                var c = findClass(
                    "com.android.server.display.DisplayManagerService",
                    loadPackageParam.classLoader
                )
                deoptimizeMethod(c, "setUserPreferredModeForDisplayLocked")
                deoptimizeMethod(c, "setUserPreferredDisplayModeInternal")
                c = findClass(
                    "com.android.server.wm.InsetsPolicy\$InsetsPolicyAnimationControlListener",
                    loadPackageParam.classLoader
                )
                for (m in c.declaredConstructors) {
                    deoptimizeMethod!!.invoke(null, m)
                }
                c = findClass(
                    "com.android.server.wm.InsetsPolicy",
                    loadPackageParam.classLoader
                )
                deoptimizeMethod(c, "startAnimation")
                deoptimizeMethod(c, "controlAnimationUnchecked")
                for (i in 0 until 20) {
                    c = findClassIfExists(
                        "com.android.server.wm.DisplayContent$\$ExternalSyntheticLambda",
                        loadPackageParam.classLoader
                    )
                    if (c != null && BiPredicate::class.java.isAssignableFrom(c)) {
                        deoptimizeMethod(c, "test")
                    }
                }
            } catch (t: Throwable) {
                Log.e("DisableFlagSecure", "Failed to deoptimize", t)
            }
        }
    }
}