package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.FieldFinder.`-Static`.fieldFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object DisableSafeModelTip : HookRegister() {
    private val MiuiSettingsAd by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "android.provider.MiuiSettings\$Ad"
//            matchType = MatchType.FULL
//        }.firstOrNull()?.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStringsMatcher {
                    this.add {
                        this.value = "android.provider.MiuiSettings\$Ad"
                        StringMatchType.Equals
                    }
                }
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("Disable_Safe_Model_Tip") {
        runCatching {
            loadClassOrNull("com.miui.packageInstaller.model.ApkInfo")!!.methodFinder()
                .filterByName("getSystemApp")
                .first().createHook {
                    returnConstant(true)
                }

            MiuiSettingsAd?.createHook {
                returnConstant(false)
            }

            val installProgressActivityClass =
                loadClassOrNull("com.miui.packageInstaller.InstallProgressActivity")!!
            installProgressActivityClass.methodFinder()
                .filterByName("g0")
                .firstOrNull()?.createHook {
                    returnConstant(false)
                }
            installProgressActivityClass.methodFinder()
                .filterByName("Q1")
                .firstOrNull()?.createHook {
                    before {
                        it.result = null
                    }
                }
            installProgressActivityClass.methodFinder()
                .filter {
                    true
                }.toList().createHooks {
                    after { param ->
                        param.thisObject.javaClass.fieldFinder().first {
                            type == Boolean::class.java
                        }.setBoolean(param.thisObject, false)
                    }
                }
        }
    }
}