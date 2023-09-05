package star.sky.voyager.hook.hooks.voiceassistant

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object ChangeBrowserForMiAi : HookRegister() {
    override fun init() = hasEnable("change_browser_for_mi_ai") {
        dexKitBridge.findMethod {
            matcher {
                name = "parseIntentData"
                parameterCount(3)
//                parameterTypes = listOf("Lcom/xiaomi/ai/api/Template\$AndroidIntent;", "", "")
                parameterTypes = listOf(
                    "com.xiaomi.ai.api.Template\$AndroidIntent",
                    "com.xiaomi.ai.api.Template\$Task",
                    "com.xiaomi.ai.api.Template\$FullScreen"
                )
            }
//            methodName = "parseIntentData"
//            methodParamTypes = arrayOf("Lcom/xiaomi/ai/api/Template\$AndroidIntent;", "", "")
        }.map { it.getMethodInstance(safeClassLoader) }.createHooks {
            before {
                if (invokeMethodBestMatch(
                        it.args[0],
                        "getPkgName"
                    )?.equals("com.android.browser") == true
                ) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(invokeMethodBestMatch(it.args[0], "getUri") as String?)
                    )
                    @SuppressLint("QueryPermissionsNeeded") val packageName: String =
                        intent.resolveActivityInfo(appContext.packageManager, 0).packageName
                    invokeMethodBestMatch(it.args[0], "setPkgName", null, packageName)
                }
            }
        }
    }
}