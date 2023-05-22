package star.sky.voyager.hook.hooks.music

import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemoveOpenAd : HookRegister() {
    override fun init() = hasEnable("remove_open_ad") {
        loadClass("com.tencent.qqmusiclite.activity.SplashAdActivity").methodFinder()
            .forEach { method ->
                if (method.name == "onCreate" && method.parameterTypes[0] == Bundle::class.java) {
                    method.createHook {
                        after { param ->
                            loadClassOrNull("android.app.Activity")
                                ?.getMethod("finish")
                                ?.invoke(param.thisObject)
                        }
                    }
                }
            }
    }
}