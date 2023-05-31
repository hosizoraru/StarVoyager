package star.sky.voyager.hook.hooks.mediaeditor

import android.os.Build
import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object FilterManager : HookRegister() {
    private lateinit var device: String
    override fun init() = hasEnable("filter_manager") {
        loadDexKit()
        val methods = dexKitBridge.findMethodUsingString {
            usingString = "wayne"
            matchType = MatchType.FULL
        }.filter { it.isMethod }.map { it.getMethodInstance(safeClassLoader) }.toTypedArray()
        MethodFinder.fromArray(methods).first {
            returnType == List::class.java || (parameterCount == 1 && parameterTypes[0] == Bundle::class.java)
        }.createHook {
            before {
                if (!this@FilterManager::device.isInitialized) {
                    device = Build.DEVICE
                }
                setStaticObject(loadClass("android.os.Build"), "DEVICE", "wayne")
            }
            after {
                setStaticObject(loadClass("android.os.Build"), "DEVICE", device)
            }
        }
    }
}