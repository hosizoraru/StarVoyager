package star.sky.voyager.hook.hooks.mediaeditor

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.voyager.LazyClass.AndroidBuildCls
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object FilterManager : HookRegister() {
    private lateinit var device: String
    private val methods by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "wayne"
//            matchType = MatchType.FULL
//        }.filter { it.isMethod }.map { it.getMethodInstance(safeClassLoader) }.toTypedArray()
//            .firstOrNull()
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("wayne")
            }
        }.filter { it.isMethod }.map { it.getMethodInstance(safeClassLoader) }.toList()
    }

    override fun init() = hasEnable("filter_manager") {
//        val methods = dexKitBridge.findMethodUsingString {
//            usingString = "wayne"
//            matchType = MatchType.FULL
//        }.filter { it.isMethod }.map { it.getMethodInstance(safeClassLoader) }.toTypedArray()
////        returnType == List::class.java || (parameterCount == 1 && parameterTypes[0] == Bundle::class.java)
//        MethodFinder.fromArray(methods).first()
        methods.createHooks {
            before {
                if (!this@FilterManager::device.isInitialized) {
                    device = Build.DEVICE
                }
                setStaticObject(AndroidBuildCls, "DEVICE", "wayne")
            }
            after {
                setStaticObject(AndroidBuildCls, "DEVICE", device)
            }
        }
    }
}