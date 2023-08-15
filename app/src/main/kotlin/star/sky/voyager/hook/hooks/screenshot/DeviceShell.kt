package star.sky.voyager.hook.hooks.screenshot

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.AndroidBuildCls

object DeviceShell : HookRegister() {
    private lateinit var device: String
    private val deviceS by lazy {
        getString("device_shell_s", "ishtar")
    }

    override fun init() = hasEnable("device_shell") {
        loadClass("com.miui.gallery.editor.photo.screen.shell.res.ShellResourceFetcher").methodFinder()
            .filterByName("getResId")
            .first().createHook {
                before {
                    if (!this@DeviceShell::device.isInitialized) {
                        device = Build.DEVICE
                    }
                    setStaticObject(AndroidBuildCls, "DEVICE", deviceS)
                }

                after {
                    setStaticObject(AndroidBuildCls, "DEVICE", device)
                }
            }
    }
}