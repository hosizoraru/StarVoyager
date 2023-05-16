package star.sky.voyager.hook.hooks.market

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.yife.XSharedPreferences

object AsWhat : HookRegister() {
    override fun init() {
        val qwq = XSharedPreferences.getString("Market_As","Default")
        loadClass("com.xiaomi.market.MarketApp").constructors.createHooks {
            before {
                when(qwq) {
                    "Default"-> {
                        Log.i("Use Your Default Device.")
                        return@before
                    }
                    "Mi13Pro"-> {
                        Log.i("Use Mi13Pro.")
                        XposedHelpers.setStaticObjectField(Build::class.java, "DEVICE", "nuwa")
                        XposedHelpers.setStaticObjectField(Build::class.java, "MODEL", "2210132C")
                        XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }
                    "Mi13Ultra"-> {
                        Log.i("Use Mi13Ultra.")
                        XposedHelpers.setStaticObjectField(Build::class.java, "DEVICE", "ishtar")
                        XposedHelpers.setStaticObjectField(Build::class.java, "MODEL", "2304FPN6DC")
                        XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }
                    "MiPad5Pro12.4"-> {
                        Log.i("Use MiPad5Pro12.4.")
                        XposedHelpers.setStaticObjectField(Build::class.java,"DEVICE", "dagu")
                        XposedHelpers.setStaticObjectField(Build::class.java,"MODEL", "22081281AC")
                        XposedHelpers.setStaticObjectField(Build::class.java,"MANUFACTURER", "Xiaomi")
                    }
                    "MiPad6Pro"-> {
                        Log.i("Use MiPad6Pro.")
                        XposedHelpers.setStaticObjectField(Build::class.java,"DEVICE", "liuqin")
                        XposedHelpers.setStaticObjectField(Build::class.java,"MODEL", "23046RP50C")
                        XposedHelpers.setStaticObjectField(Build::class.java,"MANUFACTURER", "Xiaomi")
                    }
                    "MixFold2"-> {
                        Log.i("Use MixFold2.")
                        XposedHelpers.setStaticObjectField(Build::class.java, "DEVICE", "zizhan")
                        XposedHelpers.setStaticObjectField(Build::class.java, "MODEL", "22061218C")
                        XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", "Xiaomi")
                    }
                }
            }
        }
    }
}