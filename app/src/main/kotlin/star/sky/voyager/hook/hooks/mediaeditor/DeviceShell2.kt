package star.sky.voyager.hook.hooks.mediaeditor

import android.os.Build
import androidx.annotation.RequiresApi
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.AndroidBuildCls
import star.sky.voyager.utils.voyager.LazyClass.ShellResourceFetcher
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object DeviceShell2 : HookRegister() {
    private lateinit var device: String
    private val deviceS2 by lazy {
        getString("device_shell_s2", "raphael")
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun init() = hasEnable("device_shell2") {
        dexKitBridge.findMethodUsingString {
            usingString = "from_partial_screenshot"
            matchType = MatchType.FULL
        }.single().getMethodInstance(classLoader).createHook {
            returnConstant(true)
        }

        ShellResourceFetcher.methodFinder()
            .filterByName("getResId")
            .first().createHook {
                before {
                    if (!this@DeviceShell2::device.isInitialized) {
                        device = Build.DEVICE
                    }
                    setStaticObject(
                        AndroidBuildCls,
                        "DEVICE",
                        deviceS2
                    )
                }

                after {
                    setStaticObject(
                        AndroidBuildCls,
                        "DEVICE",
                        device
                    )
                }
            }

//        val shellInfoBean =
//            loadClass("com.miui.gallery.editor.photo.screen.shell.ShellInfoBean")
//        shellInfoBean.declaredFields
//            .forEach { fields ->
//                fields.isAccessible = true
//                if (fields.name == "height") {
//                    Log.i("qwq: ${fields.name} : $fields}}")
//                    val shellInfoBeanInstance = shellInfoBean.newInstance()
//                    val fields2 = shellInfoBean.getDeclaredField("height")
//                    fields2.isAccessible = true
//                    fields2.setFloat(shellInfoBeanInstance, 2400f)
//                    Log.i("qwq: ${fields.name} : ${fields2.get(shellInfoBeanInstance)}")
//                }
//            }
    }
}