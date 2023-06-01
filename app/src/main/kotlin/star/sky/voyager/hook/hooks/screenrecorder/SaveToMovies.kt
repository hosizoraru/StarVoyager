package star.sky.voyager.hook.hooks.screenrecorder

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SaveToMovies : HookRegister() {
    override fun init() = hasEnable("save_to_movies") {
        setStaticObject(loadClass("android.os.Environment"), "DIRECTORY_DCIM", "Movies")
        loadClass("android.content.ContentValues").methodFinder()
            .filterByName("put")
            .filterByParamTypes(String::class.java, String::class.java)
            .toList().createHooks {
                before { param ->
                    if (param.args[0] == "relative_path") {
                        param.args[1] = (param.args[1] as String).replace("DCIM", "Movies")
                    }
                }
            }
    }
}