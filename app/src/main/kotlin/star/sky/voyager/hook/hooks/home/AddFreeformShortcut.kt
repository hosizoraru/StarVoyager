package star.sky.voyager.hook.hooks.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.ComponentName
import android.content.Intent
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.getStaticObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AddFreeformShortcut : HookRegister() {
    @SuppressLint("DiscouragedApi")
    override fun init() = hasEnable("add_freeform_shortcut") {
        val clazzSystemShortcutMenuItem =
            loadClass("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem")

        Activity::class.java.methodFinder().filterByName("onCreate").toList().createHooks {
            after {
                EzXHelper.initAppContext(it.thisObject as Activity)
            }
        }
        loadClass("com.miui.home.launcher.util.ViewDarkModeHelper").methodFinder()
            .filterByName("onConfigurationChanged").toList().createHooks {
                after {
                    invokeStaticMethodBestMatch(
                        clazzSystemShortcutMenuItem,
                        "createAllSystemShortcutMenuItems"
                    )
                }
            }
        loadClass("com.miui.home.launcher.shortcuts.ShortcutMenuItem").methodFinder()
            .filterByName("getShortTitle")
            .toList().createHooks {
                after { param ->
                    if (param.result.equals("应用信息")) {
                        param.result = "信息"
                    }
                }
            }
        loadClass("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem\$AppDetailsShortcutMenuItem")
            .methodFinder()
            .filterByName("getOnClickListener").toList().createHooks {
                before { param ->
                    when (invokeMethodBestMatch(param.thisObject, "getShortTitle")) {
                        EzXHelper.moduleRes.getString(R.string.freeform) -> {
                            param.result = View.OnClickListener { view ->
                                val context = view.context
                                val componentName = XposedHelpers.callMethod(
                                    param.thisObject,
                                    "getComponentName"
                                ) as ComponentName
                                val intent = Intent().apply {
                                    action = "android.intent.action.MAIN"
                                    addCategory("android.intent.category.DEFAULT")
                                    component = componentName
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                invokeStaticMethodBestMatch(
                                    loadClass("com.miui.launcher.utils.ActivityUtilsCompat"),
                                    "makeFreeformActivityOptions",
                                    null,
                                    context,
                                    componentName.packageName
                                )?.let {
                                    context.startActivity(
                                        intent,
                                        (it as ActivityOptions).toBundle()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        loadClass("com.miui.home.launcher.shortcuts.SystemShortcutMenu").methodFinder()
            .filterByName("getMaxShortcutItemCount").toList().createHooks {
                after { param ->
                    param.result = 5
                }
            }
        loadClass("com.miui.home.launcher.shortcuts.AppShortcutMenu").methodFinder()
            .filterByName("getMaxShortcutItemCount").toList().createHooks {
                after { param ->
                    param.result = 5
                }
            }
        clazzSystemShortcutMenuItem.methodFinder()
            .filterByName("createAllSystemShortcutMenuItems").toList().createHooks {
                after {
                    val mAllSystemShortcutMenuItems = getStaticObjectOrNullAs<List<Any>>(
                        clazzSystemShortcutMenuItem,
                        "sAllSystemShortcutMenuItems"
                    )!!
                    val mSmallWindowInstance =
                        loadClass("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem\$AppDetailsShortcutMenuItem")
                            .newInstance()
                            .apply {
                                invokeMethodBestMatch(
                                    this,
                                    "setShortTitle",
                                    null,
                                    EzXHelper.moduleRes.getString(R.string.freeform)
                                )
                                invokeMethodBestMatch(
                                    this,
                                    "setIconDrawable",
                                    null,
                                    EzXHelper.appContext.let {
                                        it.getDrawable(
                                            it.resources.getIdentifier(
                                                "ic_task_small_window", "drawable", hostPackageName
                                            )
                                        )
                                    }
                                )
                            }

                    val sAllSystemShortcutMenuItems = ArrayList<Any>().apply {
                        add(mSmallWindowInstance)
                        addAll(mAllSystemShortcutMenuItems)
                    }
                    setStaticObject(
                        clazzSystemShortcutMenuItem,
                        "sAllSystemShortcutMenuItems",
                        sAllSystemShortcutMenuItems
                    )
                }
            }
    }
}