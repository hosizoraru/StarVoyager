package star.sky.voyager.hook.hooks.providersdownloads

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemoveXlDownload : HookRegister() {
    override fun init() = hasEnable("remove_xl_download") {
//        val targetPath = File(Environment.getExternalStorageDirectory(), ".xlDownload").absoluteFile
//        File::class.java.methodFinder().filterByName("mkdirs").first().createHook {
//            before {
//                if ((it.thisObject as File).absoluteFile.equals(targetPath)) {
//                    it.throwable = FileNotFoundException("blocked")
//                }
//            }
//        }
        val clazzXLConfig =
            loadClass("com.android.providers.downloads.config.XLConfig")

        clazzXLConfig.methodFinder().filter {
            name in setOf("setDebug", "setSoDebug")
        }.toList().createHooks {
            returnConstant(null)
        }
    }
}