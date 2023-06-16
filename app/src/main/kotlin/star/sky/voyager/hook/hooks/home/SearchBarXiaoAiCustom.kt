package star.sky.voyager.hook.hooks.home

import android.content.ComponentName
import android.content.Intent
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object SearchBarXiaoAiCustom : HookRegister() {
    override fun init() = hasEnable("search_bar_xiaoai_custom") {
        val searchBarXiaoaiLayout =
            loadClass("com.miui.home.launcher.SearchBarXiaoaiLayout")
        val pkgName =
            getString("search_bar_pkg_name", "com.google.android.googlequicksearchbox")!!
        val clsName =
            getString("search_bar_cls_name", "com.google.android.apps.searchlite.SearchActivity")!!

        searchBarXiaoaiLayout.methodFinder()
            .filterByName("launchXiaoAi")
            .first().createHook {
                before { param ->
                    val layout = param.thisObject as? View

                    // 检查 layout 是否为 SearchBarXiaoaiLayout 的实例
                    layout?.let {
                        val context = layout.context

                        // 构建打开 Google 搜索应用的 Intent
                        val intent = Intent(Intent.ACTION_MAIN).apply {
                            component = ComponentName(
                                pkgName,
                                clsName
                            )
                            addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK
                                        or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                            )
                        }

                        // 启动应用
                        context.startActivity(intent)

                        // 取消原始方法的调用
                        param.result = null
                    }
                }
            }
    }
}