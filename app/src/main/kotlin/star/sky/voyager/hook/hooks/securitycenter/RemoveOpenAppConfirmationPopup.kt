package star.sky.voyager.hook.hooks.securitycenter

import android.annotation.SuppressLint
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemoveOpenAppConfirmationPopup : HookRegister() {
    @SuppressLint("DiscouragedApi")
    override fun init() = hasEnable("remove_open_app_confirmation_popup") {
        loadClass("android.widget.TextView").methodFinder()
            .filterByName("setText")
            .filterByParamTypes(CharSequence::class.java)
            .first().createHook {
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