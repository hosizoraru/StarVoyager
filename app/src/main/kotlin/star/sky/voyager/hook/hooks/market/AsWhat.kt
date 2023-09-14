package star.sky.voyager.hook.hooks.market

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.XSharedPreferences.getString

object AsWhat : HookRegister() {
    private val qwq by lazy {
        getString("Market_As", "Default")
    }
    private val device by lazy {
        getString("market_as_device", "ishtar")
    }
    private val recommend by lazy {
        getBoolean("device_market_recommend", false)
    }
    private val mod by lazy {
        getBoolean("device_market_mod", false)
    }

    override fun init() = hasEnable("device_market") {
        if (!recommend && !mod) return@hasEnable
        val marketAppCls =
            loadClass("com.xiaomi.market.MarketApp")

        marketAppCls.constructors.createHooks {
            before {
                val targetDevice = if (recommend && !mod) {
                    when (qwq) {
                        "Mi13Pro" -> "nuwa"
                        "Mi13Ultra" -> "ishtar"
                        "MiPad6Pro" -> "liuqin"
                        "MiPad6Max" -> "yudi"
                        "MixFold3" -> "babylon"
                        "Default" -> null
                        else -> null
                    }
                } else {
                    device
                }

                targetDevice?.let { setDevice(it) }
            }
        }
    }

    private fun setDevice(deviceName: String) {
        setStaticObject(Build::class.java, "DEVICE", deviceName)
    }
}