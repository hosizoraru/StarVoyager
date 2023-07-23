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
    override fun init() {
        when (hostPackageName) {
            "com.miui.gallery" -> {
                hasEnable("gallery_super_clipboard") {
                    methodSuperClipboard("com.miui.gallery.util.MiscUtil")
                }
            }

            "com.android.fileexplorer" -> {
                hasEnable("file_explorer_super_clipboard") {
                    methodSuperClipboard("com.android.fileexplorer.model.Util")
                }
            }

            "com.miui.screenshot" -> {
                hasEnable("screen_shot_super_clipboard") {
                    dexKitSuperClipboard()
                }
            }

            "com.android.browser" -> {
                hasEnable("browser_super_clipboard") {
                    dexKitSuperClipboard()
                }
            }

            "com.miui.notes" -> {
                hasEnable("notes_super_clipboard") {
                    methodSuperClipboard("com.miui.common.tool.Utils")
                }
            }
        }
    }

    private fun methodSuperClipboard(clsName: String) {
        loadClass(clsName).methodFinder()
            .filterByName("isSupportSuperClipboard")
            .first().createHook {
                returnConstant(true)
            }
    }

    private fun dexKitSuperClipboard() {
        safeDexKitBridge.findMethodUsingString {
            usingString = "ro.miui.support_super_clipboard"
            matchType = MatchType.FULL
            methodReturnType = "boolean"
        }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
            returnConstant(true)
        }
    }
}