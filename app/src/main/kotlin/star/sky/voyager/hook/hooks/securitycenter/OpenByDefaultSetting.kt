package star.sky.voyager.hook.hooks.securitycenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.verify.domain.DomainVerificationManager
import android.view.View
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.initAppContext
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNull
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object OpenByDefaultSetting : HookRegister() {
    private val openByDefault by lazy {
        moduleRes.getString(R.string.open_by_default)
    }
    private val linkOpenAlways by lazy {
        moduleRes.getString(R.string.app_link_open_always)
    }
    private val linkOpenNever by lazy {
        moduleRes.getString(R.string.app_link_open_never)
    }

    @SuppressLint("DiscouragedApi")
    override fun init() = hasEnable("open_by_default_setting") {
        val domainVerificationManager: DomainVerificationManager by lazy {
            appContext.getSystemService(
                DomainVerificationManager::class.java
            )
        }
        loadClass("com.miui.appmanager.ApplicationsDetailsActivity").methodFinder()
            .filterByName("onClick")
            .first().createHook {
                before { param ->
                    initAppContext(param.thisObject as Activity)
                    val clickedView = param.args[0]
                    val cleanOpenByDefaultView = (param.thisObject as Activity).findViewById<View>(
                        appContext.resources.getIdentifier(
                            "am_detail_default", "id", hostPackageName
                        )
                    )
                    val pkgName =
                        (param.thisObject as Activity).intent.getStringExtra("package_name")!!
                    if (clickedView == cleanOpenByDefaultView) {
                        val intent = Intent().apply {
                            action = android.provider.Settings.ACTION_APP_OPEN_BY_DEFAULT_SETTINGS
                            addCategory(Intent.CATEGORY_DEFAULT)
                            data = android.net.Uri.parse("package:${pkgName}")
                            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                        }
                        invokeMethodBestMatch(param.thisObject, "startActivity", null, intent)
                        param.result = null
                    }
                }
            }

        dexKitBridge.findMethod {
            matcher {
                addUsingString("enter_way", StringMatchType.Contains)
                declaredClass = "com.miui.appmanager.ApplicationsDetailsActivity"
                returnType = "void"
//                parameterTypes = listOf("", "Ljava/lang/Boolean;")
            }
        }.firstOrNull()?.getMethodInstance(safeClassLoader)?.createHook {
            after { param ->
                initAppContext(param.thisObject as Activity)
                val cleanOpenByDefaultView = (param.thisObject as Activity).findViewById<View>(
                    appContext.resources.getIdentifier(
                        "am_detail_default", "id", hostPackageName
                    )
                )
                val pkgName = (param.thisObject as Activity).intent.getStringExtra("package_name")!!
                val isLinkHandlingAllowed =
                    domainVerificationManager.getDomainVerificationUserState(
                        pkgName
                    )?.isLinkHandlingAllowed ?: false
                val subTextId =
                    if (isLinkHandlingAllowed) linkOpenAlways else linkOpenNever
                cleanOpenByDefaultView::class.java.declaredFields.forEach {
                    val view = getObjectOrNull(cleanOpenByDefaultView, it.name)
                    if (view !is TextView) return@forEach
                    invokeMethodBestMatch(
                        view,
                        "setText",
                        null,
                        openByDefault
                    )
                }
                invokeMethodBestMatch(
                    cleanOpenByDefaultView,
                    "setSummary",
                    null,
                    subTextId
                )
            }
        }
    }
}