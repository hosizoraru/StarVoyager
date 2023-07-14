package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.closeDexKit
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object SuperClipboard : HookRegister() {
    override fun init() = hasEnable("super_clipboard") {
        when (hostPackageName) {
            "com.miui.gallery" -> {
                loadClass("com.miui.gallery.util.MiscUtil").methodFinder()
                    .filterByName("isSupportSuperClipboard")
                    .first().createHook {
                        returnConstant(true)
                    }
            }

            "com.android.fileexplorer" -> {
//                loadDexKit()
//                dexKitBridge.findMethodUsingString {
//                    usingString = "ro.miui.support_super_clipboard"
//                    matchType = MatchType.FULL
//                    methodReturnType = "boolean"
//                }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
//                    returnConstant(true)
//                }
                loadClass("com.android.fileexplorer.model.Util").methodFinder()
                    .filterByName("isSupportSuperClipboard")
                    .first().createHook {
                        returnConstant(true)
                    }
            }

            "com.miui.screenshot" -> {
                loadDexKit()
                dexKitBridge.findMethodUsingString {
                    usingString = "ro.miui.support_super_clipboard"
                    matchType = MatchType.FULL
                    methodReturnType = "boolean"
                }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
                    returnConstant(true)
                }
                closeDexKit()
            }

            "com.android.browser" -> {
                loadDexKit()
                dexKitBridge.findMethodUsingString {
                    usingString = "ro.miui.support_super_clipboard"
                    matchType = MatchType.FULL
                    methodReturnType = "boolean"
                }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
                    returnConstant(true)
                }
                closeDexKit()
            }
        }
    }
}