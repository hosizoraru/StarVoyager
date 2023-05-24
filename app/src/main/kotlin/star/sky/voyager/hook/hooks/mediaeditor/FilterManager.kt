package star.sky.voyager.hook.hooks.mediaeditor

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.FieldFinder.`-Static`.fieldFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object FilterManager : HookRegister() {
    override fun init() = hasEnable("filter_manager") {
        loadDexKit()
        dexKitBridge.findMethodUsingString {
            usingString = "wayne"
            methodReturnType = "Ljava/util/List;"
            matchType = MatchType.FULL
        }.single().getMethodInstance(classLoader).createHook {
            before {
                loadClass("android.os.Build").fieldFinder().first {
                    type == String::class.java && name == "DEVICE"
                }.set(null, "wayne")
            }
        }
    }
}