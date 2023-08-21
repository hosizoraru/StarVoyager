package star.sky.voyager.hook.hooks.securitycenter

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object LockOneHundred : HookRegister() {
    private val score by lazy {
        dexKitBridge.findMethodUsingString {
            usingString = "getMinusPredictScore"
            matchType = MatchType.CONTAINS
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("lock_one_hundred") {
        loadClass("com.miui.securityscan.ui.main.MainContentFrame").methodFinder()
            .filterByName("onClick")
            .filterByParamTypes(View::class.java)
            .first().createHook {
                before {
                    it.result = null
                }
            }

        score?.createHook {
            returnConstant(0)
        }
    }
}