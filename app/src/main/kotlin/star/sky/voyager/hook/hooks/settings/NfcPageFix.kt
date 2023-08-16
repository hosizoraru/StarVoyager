package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister

object NfcPageFix : HookRegister() {
    override fun init() {
        loadClass("com.android.settings.nfc.MiuiNfcDetail").methodFinder()
            .filterByName("createPreferenceControllers")
            .first().createHook {
                after { param ->
                    val mController = param.result as ArrayList<Any>
//                    val clsInstance =
//                        XposedHelpers.newInstance(loadClass("com.android.settings.nfc.NfcPaymentPreferenceController"))
//                    mController.add(1, clsInstance)
                    val mContent = param.args[0]
                    Log.i("Controller: $mController")
                    Log.i("Content: $mContent")
                }
            }
    }
}