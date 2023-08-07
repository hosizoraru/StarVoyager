package star.sky.voyager.hook.hooks.providersdownloads

import android.os.Environment
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import java.io.File
import java.io.FileNotFoundException

object RemoveXlDownload : HookRegister() {
    override fun init() = hasEnable("remove_xl_download") {
        val targetPath = File(Environment.getExternalStorageDirectory(), ".xlDownload").absoluteFile
        File::class.java.methodFinder().filterByName("mkdirs").first().createHook {
            before {
                if ((it.thisObject as File).absoluteFile.equals(targetPath)) {
                    it.throwable = FileNotFoundException("blocked")
                }
            }
        }
    }
}