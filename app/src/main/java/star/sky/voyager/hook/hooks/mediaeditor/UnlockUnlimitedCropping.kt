package star.sky.voyager.hook.hooks.mediaeditor

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UnlockUnlimitedCropping : HookRegister() {
    override fun init() = hasEnable("unlock_unlimited_cropping") {
        val resizeDetectorClass = loadClassOrNull("com.miui.gallery.editor.photo.core.imports.obsoletes.Crop\$ResizeDetector")
        if (resizeDetectorClass != null) {
            loadClass("com.miui.gallery.editor.photo.core.imports.obsoletes.Crop\$ResizeDetector").methodFinder().first {
                name == "calculateMinSize"
            }.createHook {
                before {
                    it.result = 0
                }
            }
        } else {
            var resizeDetector = 'a'
            for (i in 0..25) {
                try {
                    loadClass("com.miui.gallery.editor.photo.screen.crop.ScreenCropView\$${resizeDetector}").methodFinder().first {
                        returnType == Int::class.javaPrimitiveType && parameterCount == 0
                    }.createHook {
                        before {
                            it.result = 0
                        }
                    }
                } catch (_: Throwable) {
                    resizeDetector++
                }
            }
        }
        val resizeDetectorClass1 = loadClassOrNull("com.miui.gallery.editor.photo.screen.crop.ScreenCropView\$ResizeDetector")
        if (resizeDetectorClass1 != null) {
            loadClass("com.miui.gallery.editor.photo.screen.crop.ScreenCropView\$ResizeDetector").methodFinder().first {
                name == "calculateMinSize"
            }.createHook {
                before {
                    it.result = 0
                }
            }
        } else {
            var resizeDetector1 = 'a'
            for (i in 0..25) {
                try {
                    loadClass("com.miui.gallery.editor.photo.core.imports.obsoletes.Crop\$${resizeDetector1}").methodFinder().first {
                        returnType == Int::class.javaPrimitiveType && parameterCount == 0
                    }.createHook {
                        before {
                            it.result = 0
                        }
                    }
                } catch (_: Throwable) {
                    resizeDetector1++
                }
            }
        }
    }
}