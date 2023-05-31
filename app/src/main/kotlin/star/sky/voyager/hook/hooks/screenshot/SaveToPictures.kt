package star.sky.voyager.hook.hooks.screenshot

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SaveToPictures : HookRegister() {
    override fun init() = hasEnable("save_to_pictures") {
        setStaticObject(loadClass("android.os.Environment"), "DIRECTORY_DCIM", "Download")
    }
}