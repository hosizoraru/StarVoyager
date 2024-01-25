package star.sky.voyager.hook.hooks.weather

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object GlobalWeather : HookRegister() {
    private val weatherGlobal by lazy {
        dexKitBridge.findMethod {
            matcher {
                returnType = "boolean"
                paramCount = 0
                addCall {
                    usingStrings = listOf(
                        "Wth2:ActivityWeatherMain", "onCreate()"
                    )
                }
            }
        }.map { it.getMethodInstance(classLoader) }
    }

    override fun init() = hasEnable("global_weather_flag") {
        weatherGlobal.createHooks {
            returnConstant(false)
        }
    }
}