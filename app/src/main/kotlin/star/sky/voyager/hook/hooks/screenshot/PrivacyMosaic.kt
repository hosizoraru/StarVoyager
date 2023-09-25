package star.sky.voyager.hook.hooks.screenshot

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object PrivacyMosaic : HookRegister() {
    override fun init() = hasEnable("privacy_mosaic") {
        loadClass("com.miui.gallery.editor.photo.screen.mosaic.ScreenMosaicView")
            .methodFinder().filterByName("isSupportPrivacyMarking")
            .first().createHook {
                returnConstant(true)
            }
    }
}