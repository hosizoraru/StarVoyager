package star.sky.voyager.hook.hooks.securitycenter

import android.content.Context
import android.util.ArrayMap
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object GetDefaultBubbles : HookRegister() {
    override fun init() = hasEnable("remove_conversation_bubble_settings_restriction") {
        loadClass("com.miui.bubbles.settings.BubblesSettings").methodFinder()
            .filterByName("getDefaultBubbles")
            .first().createHook {
                before { param ->
                    val clazzBubbleApp = loadClass("com.miui.bubbles.settings.BubbleApp")
                    val arrayMap = ArrayMap<String, Any>()
                    val mContext =
                        param.thisObject.objectHelper().getObjectOrNullAs<Context>("mContext")
                    val mCurrentUserId =
                        param.thisObject.objectHelper().getObjectOrNullAs<Int>("mCurrentUserId")
                    val freeformSuggestionList = invokeStaticMethodBestMatch(
                        loadClass("android.util.MiuiMultiWindowUtils"),
                        "getFreeformSuggestionList",
                        null,
                        mContext
                    ) as List<*>
                    freeformSuggestionList.associateWith { pkg ->
                        clazzBubbleApp.getConstructor(String::class.java, Int::class.java)
                            .newInstance(pkg, mCurrentUserId).apply {
                                objectHelper().invokeMethodBestMatch("setChecked", null, true)
                            }
                    }.forEach { (pkg, bubbleApp) ->
                        arrayMap[pkg as String] = bubbleApp
                    }
                    param.result = arrayMap
                }
            }
    }
}