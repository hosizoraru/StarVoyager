package star.sky.voyager.hook.hooks.securitycenter

import android.annotation.SuppressLint
import android.content.Context
import android.util.ArrayMap
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import org.lsposed.hiddenapibypass.HiddenApiBypass
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemoveConversationBubbleSettingsRestriction : HookRegister() {
    @SuppressLint("PrivateApi")
    override fun init() = hasEnable("remove_conversation_bubble_settings_restriction") {
        loadClass("com.miui.bubbles.settings.BubblesSettings").methodFinder()
            .filterByName("getDefaultBubbles")
            .first().createHook {
                before { param ->
                    val classBubbleApp = loadClass("com.miui.bubbles.settings.BubbleApp")
                    val arrayMap = ArrayMap<String, Any>()
                    val mContext = param.thisObject.getObjectFieldAs<Context>("mContext")
                    val mCurrentUserId = param.thisObject.getObjectFieldAs<Int>("mCurrentUserId")
                    val freeformSuggestionList = HiddenApiBypass.invoke(
                        Class.forName("android.util.MiuiMultiWindowUtils"),
                        null,
                        "getFreeformSuggestionList",
                        mContext
                    ) as List<*>
                    if (freeformSuggestionList.isNotEmpty()) {
                        for (str in freeformSuggestionList) {
                            val bubbleApp = classBubbleApp.getConstructor(
                                String::class.java, Int::class.java
                            ).newInstance(str, mCurrentUserId)
                            classBubbleApp.getMethod("setChecked", Boolean::class.java)
                                .invoke(bubbleApp, true)
                            arrayMap[str as String] = bubbleApp
                        }
                    }
                    param.result = arrayMap
                }
            }
    }
}