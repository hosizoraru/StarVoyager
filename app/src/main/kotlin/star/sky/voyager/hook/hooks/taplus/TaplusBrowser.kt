package star.sky.voyager.hook.hooks.taplus

import android.content.Intent
import android.net.Uri
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object TaplusBrowser : HookRegister() {
    override fun init() = hasEnable("taplus_browser") {
        val browserPkg by lazy {
            getString("taplus_browser_pkg", "com.android.browser")!!
        }
        loadClass("com.miui.contentextension.utils.AppsUtils").methodFinder()
            .filterByName("getIntentWithBrowser")
            .first().createHook {
                before { param ->
                    val url = param.args[0] as String
                    val newIntent = createCustomBrowserIntent(url, browserPkg)
                    param.result = newIntent
                }
            }
    }

    private fun createCustomBrowserIntent(url: String, packageName: String): Intent {
        val intent = Intent("android.intent.action.VIEW")
        intent.data = Uri.parse(url)
        intent.setPackage(packageName)
        intent.putExtra("activity_resizeable", true)
        return intent
    }
}