package star.sky.voyager.hook.hooks.personalassistant

import android.content.res.Configuration
import android.view.Window
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.getIntField
import star.sky.voyager.utils.api.hookBeforeAllMethods
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.api.new
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.BlurDraw.getValueByFields
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import kotlin.math.abs

object BlurPersonalAssistant : HookRegister() {
    private val blurRadius by lazy {
        getInt("blur_personal_assistant_radius", 80)
    }
    private val xiaomiMethod by lazy {
        getBoolean("blur_personal_assistant_xiaomi_method", false)
    }
    private val ScrollStateManager by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "ScrollStateManager",
                    "Manager must be init before using"
                )
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("blur_personal_assistant") {
        when (xiaomiMethod) {
            true -> initMiui()
            false -> initWini()
        }
    }

    private fun initWini() {
        var lastBlurRadius = -1
        ScrollStateManager?.createHook {
            after { param ->
                val scrollX =
                    param.args[0] as Float
                val fieldNames = ('a'..'z').map { it.toString() }
                val window =
                    getValueByFields(param.thisObject, fieldNames) ?: return@after
                if (window.javaClass.name.contains("Window")) {
                    window as Window
                    val blurRadius1 =
                        (scrollX * blurRadius).toInt()
                    if (abs(blurRadius1 - lastBlurRadius) > 2) {
                        window.setBackgroundBlurRadius(blurRadius1)
                        lastBlurRadius = blurRadius1
                    }
                }
            }
        }
    }

    private fun initMiui() {
        val deviceAdapter =
            loadClass("com.miui.personalassistant.device.DeviceAdapter")
        val foldableDeviceAdapter =
            loadClass("com.miui.personalassistant.device.FoldableDeviceAdapter")

        deviceAdapter.hookBeforeAllMethods("create") {
            it.result = foldableDeviceAdapter.new(it.args[0])
        }
        foldableDeviceAdapter.apply {
            runCatching {
                hookBeforeMethod("onEnter", Boolean::class.java) {
                    it.thisObject.objectHelper().setObject("mScreenSize", 3)
                }
            }.onFailure {
                hookBeforeMethod("onOpened") {
                    it.thisObject.objectHelper().setObject("mScreenSize", 3)
                }
            }

            hookBeforeMethod("onConfigurationChanged", Configuration::class.java) {
                it.thisObject.objectHelper().setObject("mScreenSize", 3)
            }

            replaceMethod("onScroll", Float::class.java) {
                val f = it.args[0] as Float
                val i = (f * 100.0f).toInt()
                val mCurrentBlurRadius: Int = it.thisObject.getIntField("mCurrentBlurRadius")
                if (mCurrentBlurRadius != i) {
                    if (mCurrentBlurRadius <= 0 || i >= 0) {
                        it.thisObject.objectHelper().setObject("mCurrentBlurRadius", i)
                    } else {
                        it.thisObject.objectHelper().setObject("mCurrentBlurRadius", 0)
                    }
                    it.thisObject.callMethod("blurOverlayWindow", mCurrentBlurRadius)
                }
            }
        }
    }
}