package star.sky.voyager.hook.hooks.securitycenter

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemoveOpenAppConfirmationPopup : HookRegister() {
    override fun init() = hasEnable("remove_open_app_confirmation_popup") {
        loadClass("android.widget.TextView").methodFinder().first {
            name == "setText" && parameterTypes[0] == CharSequence::class.java
        }.createHook {
            after {
                val textView = it.thisObject as TextView
                if (it.args.isNotEmpty() && it.args[0]?.toString().equals(
                        textView.context.resources.getString(
                            textView.context.resources.getIdentifier(
                                "button_text_accept",
                                "string",
                                textView.context.packageName
                            )
                        )
                    )
                ) {
                    textView.performClick()
                }
            }
        }
    }
}