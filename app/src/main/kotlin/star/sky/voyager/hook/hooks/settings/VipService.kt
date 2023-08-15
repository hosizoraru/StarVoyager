package star.sky.voyager.hook.hooks.settings

import android.content.Context
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SettingsFeaturesCls
import star.sky.voyager.utils.yife.Build.IS_TABLET

object VipService : HookRegister() {
    override fun init() = hasEnable("show_vip_service") {
        if (IS_TABLET) return@hasEnable
        SettingsFeaturesCls.methodFinder()
            .filterByName("isVipServiceNeeded")
            .filterByParamTypes(Context::class.java)
            .first().createHook {
                returnConstant(true)
            }
    }
}