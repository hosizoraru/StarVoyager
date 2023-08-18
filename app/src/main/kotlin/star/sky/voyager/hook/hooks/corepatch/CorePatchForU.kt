package star.sky.voyager.hook.hooks.corepatch

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.reflect.InvocationTargetException

open class CorePatchForU : CorePatchForT() {
    @Throws(
        IllegalAccessException::class,
        InvocationTargetException::class,
        InstantiationException::class
    )
    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam) {
        super.handleLoadPackage(loadPackageParam)
        findAndHookMethod(
            "com.android.server.pm.PackageManagerServiceUtils", loadPackageParam.classLoader,
            "checkDowngrade",
            "com.android.server.pm.pkg.AndroidPackage",
            "android.content.pm.PackageInfoLite",
            ReturnConstant(prefs, "down_grade", null)
        )
        findAndHookMethod("com.android.server.pm.ScanPackageUtils",
            loadPackageParam.classLoader,
            "assertMinSignatureSchemeIsValid",
            "com.android.server.pm.pkg.AndroidPackage",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if (prefs.getBoolean(
                            "auth_creak",
                            true
                        )
                    ) {
                        param.result = null
                    }
                }
            })
    }
}