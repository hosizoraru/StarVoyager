package star.sky.voyager.hook.apps

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.utils.init.AppRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.FeatureParserCls

object Updater : AppRegister() {
    override val packageName: String = "com.android.updater"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        hasEnable("remove_ota_validate") {
            Array(26) { "com.android.updater.common.utils.${'a' + it}" }
                .mapNotNull { loadClassOrNull(it) }
                .firstOrNull { it.declaredFields.size >= 9 && it.declaredMethods.size > 60 }
                ?.methodFinder()?.first { name == "T" && returnType == Boolean::class.java }
                ?.createHook { returnConstant(false) }

            FeatureParserCls.methodFinder().first {
                name == "hasFeature" && parameterCount == 2
            }.createHook {
                before {
                    if (it.args[0] == "support_ota_validate") {
                        it.result = false
                    }
                }
            }

        }
    }
}