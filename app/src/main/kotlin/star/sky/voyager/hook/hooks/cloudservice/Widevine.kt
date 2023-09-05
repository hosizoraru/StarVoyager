package star.sky.voyager.hook.hooks.cloudservice

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object Widevine : HookRegister() {
    //    private var cache: Map<Int, Method> = emptyMap()
    private val WideVineL1 by lazy {
//        val versionCode =
//            getLoadPackageParam().getAppVersionCode()
//        val method = cache[versionCode] ?:
//        dexKitBridge.findMethodUsingString {
//            usingString = "persist.vendor.sys.pay.widevine"
//            matchType = MatchType.FULL
//        }.firstOrNull()?.getMethodInstance(classLoader)
//        ?: throw IllegalStateException("Method not found")
//        cache = cache.plus(versionCode to method)
//        method
        dexKitBridge.findMethod {
            matcher {
                usingStringsMatcher {
                    this.add {
                        this.value = "persist.vendor.sys.pay.widevine"
                        StringMatchType.Equals
                    }
                }
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("unlock_widevine_l1") {
        WideVineL1?.createHook {
            returnConstant(true)
        }
    }
}