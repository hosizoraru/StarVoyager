package star.sky.voyager.hook.hooks.creation

import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.MiuiBuildCls

object CreationPhone : HookRegister() {
    override fun init() = hasEnable("creation_phone") {
        setStaticObject(MiuiBuildCls, "IS_TABLET", true)
    }
}