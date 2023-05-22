package star.sky.voyager.hook.hooks.market

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.yife.XSharedPreferences

object AsWhat : HookRegister() {
    override fun init() {
        val qwq = XSharedPreferences.getString("Market_As", "Default")
        loadClass("com.xiaomi.market.MarketApp").constructors.createHooks {
            before {
                when (qwq) {
                    "Default" -> {
                        Log.i("Use Your Default Device.")
                        return@before
                    }

                    "Mi13Pro" -> {
                        Log.i("Use Mi13Pro.")
                        setStaticObject(Build::class.java, "DEVICE", "nuwa")
                        setStaticObject(Build::class.java, "MODEL", "2210132C")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }

                    "Mi13Ultra" -> {
                        Log.i("Use Mi13Ultra.")
                        setStaticObject(Build::class.java, "DEVICE", "ishtar")
                        setStaticObject(Build::class.java, "MODEL", "2304FPN6DC")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }

                    "MiPad5Pro12.4" -> {
                        Log.i("Use MiPad5Pro12.4.")
                        setStaticObject(Build::class.java, "DEVICE", "dagu")
                        setStaticObject(Build::class.java, "MODEL", "22081281AC")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }

                    "MiPad6Pro" -> {
                        Log.i("Use MiPad6Pro.")
                        setStaticObject(Build::class.java, "DEVICE", "liuqin")
                        setStaticObject(Build::class.java, "MODEL", "23046RP50C")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }

                    "MixFold2" -> {
                        Log.i("Use MixFold2.")
                        setStaticObject(Build::class.java, "DEVICE", "zizhan")
                        setStaticObject(Build::class.java, "MODEL", "22061218C")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }
                }
            }
        }
    }
}