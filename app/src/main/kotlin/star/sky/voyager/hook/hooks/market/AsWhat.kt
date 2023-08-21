package star.sky.voyager.hook.hooks.market

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.yife.XSharedPreferences.getString

object AsWhat : HookRegister() {
    private val qwq by lazy {
        getString("Market_As", "Default")
    }

    override fun init() {
        if (qwq == "Default") return
        loadClass("com.xiaomi.market.MarketApp").constructors.createHooks {
            before {
                when (qwq) {
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

                    "MiPad6Pro" -> {
                        Log.i("Use MiPad6Pro.")
                        setStaticObject(Build::class.java, "DEVICE", "liuqin")
                        setStaticObject(Build::class.java, "MODEL", "23046RP50C")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }

                    "MiPad6Max" -> {
                        Log.i("Use MiPad6Max.")
                        setStaticObject(Build::class.java, "DEVICE", "yudi")
                        setStaticObject(Build::class.java, "MODEL", "2307BRPDCC")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }

                    "MixFold3" -> {
                        Log.i("Use MixFold3.")
                        setStaticObject(Build::class.java, "DEVICE", "babylon")
                        setStaticObject(Build::class.java, "MODEL", "2308CPXD0C")
                        setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }
                }
            }
        }
    }
}