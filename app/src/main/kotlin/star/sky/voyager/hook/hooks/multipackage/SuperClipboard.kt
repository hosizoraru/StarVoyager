package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge

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
//                safeDexKitBridge.findMethodUsingString {
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
                safeDexKitBridge.findMethodUsingString {
                    usingString = "ro.miui.support_super_clipboard"
                    matchType = MatchType.FULL
                    methodReturnType = "boolean"
                }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
                    returnConstant(true)
                }
            }

            "com.android.browser" -> {
                safeDexKitBridge.findMethodUsingString {
                    usingString = "ro.miui.support_super_clipboard"
                    matchType = MatchType.FULL
                    methodReturnType = "boolean"
                }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
                    returnConstant(true)
                }
            }
        }
    }
}