package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object SuperClipboard : HookRegister() {
    override fun init() {
        when (hostPackageName) {
            "com.miui.gallery" -> {
                hasEnable("gallery_super_clipboard") {
                    dexKitSuperClipboard()
                }
            }

            "com.android.fileexplorer" -> {
                hasEnable("file_explorer_super_clipboard") {
                    dexKitSuperClipboard()
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
                    dexKitSuperClipboard()
                }
            }

            "com.miui.creation" -> {
                hasEnable("creation_super_clipboard") {
                    methodSuperClipboard("com.miui.creation.common.tools.ClipUtils")
                }
            }

            "com.miui.contentextension" -> {
                hasEnable("something") {
                    nothing()
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
        val ro by lazy {
//            dexKitBridge.findMethodUsingString {
//                usingString = "ro.miui.support_super_clipboard"
//                matchType = MatchType.FULL
//                methodReturnType = "boolean"
//            }.firstOrNull()?.getMethodInstance(safeClassLoader)
            dexKitBridge.findMethod {
                matcher {
                    addUsingStringsEquals("ro.miui.support_super_clipboard")
                    returnType = "boolean"
                }
            }.firstOrNull()?.getMethodInstance(safeClassLoader)
        }

        val sys by lazy {
//            dexKitBridge.findMethodUsingString {
//                usingString = "persist.sys.support_super_clipboard"
//                matchType = MatchType.FULL
//                methodReturnType = "boolean"
//            }.firstOrNull()?.getMethodInstance(safeClassLoader)
            dexKitBridge.findMethod {
                matcher {
                    addUsingStringsEquals("persist.sys.support_super_clipboard")
                    returnType = "boolean"
                }
            }.firstOrNull()?.getMethodInstance(safeClassLoader)
        }

        setOf(ro, sys).filterNotNull().createHooks {
            returnConstant(true)
        }
    }

    private fun nothing() {
        loadClass("com.miui.contentextension.utils.SuperImageUtils").methodFinder().filter {
            name in setOf("isSupportSuperImage", "isBitmapSupportSuperImage")
        }.toList().createHooks {
            returnConstant(true)
        }

        loadClass("com.miui.contentextension.utils.TaplusSettingUtils").methodFinder().filter {
            name in setOf("isClipboardSettingOpen", "getTaplusSetting")
        }.toList().createHooks {
            returnConstant(true)
        }

        loadClass("com.miui.contentextension.utils.ContentCatcherUtil").methodFinder().filter {
            name in setOf("isCatcherSupportClipboard", "isCatcherSupportDoublePress")
        }.toList().createHooks {
            returnConstant(true)
        }

        loadClass("com.miui.contentextension.clipboard.utils.ClipboardUtils").methodFinder()
            .filter {
                name in setOf("isPcSupport", "isRegisterCapability", "supportMail", "supportTel")
            }.toList().createHooks {
            returnConstant(true)
        }
    }
}