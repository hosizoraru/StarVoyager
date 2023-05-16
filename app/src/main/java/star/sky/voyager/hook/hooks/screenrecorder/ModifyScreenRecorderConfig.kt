package star.sky.voyager.hook.hooks.screenrecorder

import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.isFinal
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object ModifyScreenRecorderConfig : HookRegister() {
    override fun init() = hasEnable("modify_screen_recorder_config") {
        loadDexKit()
        dexKitBridge.findMethodUsingString {
            usingString = "Error when set frame value, maxValue = "
        }.map {
            it.getMethodInstance(EzXHelper.safeClassLoader)
        }.firstOrNull {
            it.parameterCount == 2 && it.parameterTypes[0] == Int::class.java && it.parameterTypes[1] == Int::class.java
        }?.createHook {
            before { param ->
                param.args[0] = 3600
                param.args[1] = 1
                param.method.declaringClass.declaredFields.firstOrNull { field ->
                    field.also {
                        it.isAccessible = true
                    }.let { fieldAccessible ->
                        fieldAccessible.isFinal &&
                                fieldAccessible.get(null).let {
                                    kotlin.runCatching {
                                        (it as IntArray).contentEquals(intArrayOf(15, 24, 30, 48, 60, 90))
                                    }.getOrDefault(false)
                                }
                    }
                }?.set(null, intArrayOf(15, 24, 30, 48, 60, 90, 120, 144))
            }
        }
        dexKitBridge.findMethodUsingString {
            usingString = "defaultBitRate = "
        }.map {
            it.getMethodInstance(EzXHelper.safeClassLoader)
        }.firstOrNull {
            it.parameterCount == 2 && it.parameterTypes[0] == Int::class.java && it.parameterTypes[1] == Int::class.java
        }?.createHook {
            before { param ->
                param.args[0] = 1200
                param.args[1] = 1
                param.method.declaringClass.declaredFields.firstOrNull { field ->
                    field.also {
                        it.isAccessible = true
                    }.let { fieldAccessible ->
                        fieldAccessible.isFinal &&
                                fieldAccessible.get(null).let {
                                    kotlin.runCatching {
                                        (it as IntArray).contentEquals(intArrayOf(200, 100, 50, 32, 24, 16, 8, 6, 4, 1))
                                    }.getOrDefault(false)
                                }
                    }
                }?.set(null, intArrayOf(1200, 1000, 800, 600, 400, 200, 100, 50, 32, 24, 16))
            }
        }
    }
}