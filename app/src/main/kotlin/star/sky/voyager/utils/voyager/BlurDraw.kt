package star.sky.voyager.utils.voyager

import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import de.robv.android.xposed.XposedHelpers.callMethod

object BlurDraw {
    fun getValueByField(target: Any, fieldName: String, clazz: Class<*>? = null): Any? {
        var targetClass = clazz
        if (targetClass == null) {
            targetClass = target.javaClass
        }
        return try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.get(target)
        } catch (e: Throwable) {
            if (targetClass.superclass == null) {
                null
            } else {
                getValueByField(target, fieldName, targetClass.superclass)
            }
        }
    }

    fun getValueByFields(target: Any, fieldNames: List<String>, clazz: Class<*>? = null): Any? {
        var targetClass = clazz ?: target.javaClass
        while (targetClass != Any::class.java) {
            for (fieldName in fieldNames) {
                try {
                    val field = targetClass.getDeclaredField(fieldName)
                    field.isAccessible = true
                    val value = field.get(target)
                    if (value is Window) {
//                    Log.i("BlurPersonalAssistant Window field name: $fieldName")
                        return value
                    }
                } catch (e: NoSuchFieldException) {
                    // This field doesn't exist in this class, skip it
                } catch (e: IllegalAccessException) {
                    // This field isn't accessible, skip it
                }
            }
            targetClass = targetClass.superclass ?: break
        }
        return null
    }

    fun createBlurDrawable(
        view: View,
        blurRadius: Int,
        cornerRadius: Int,
        color: Int? = null
    ): Drawable? {
        try {
            val mViewRootImpl = callMethod(
                view,
                "getViewRootImpl"
            ) ?: return null
            val blurDrawable = callMethod(
                mViewRootImpl,
                "createBackgroundBlurDrawable"
            ) as Drawable
            callMethod(blurDrawable, "setBlurRadius", blurRadius)
            callMethod(blurDrawable, "setCornerRadius", cornerRadius)
            if (color != null) {
                callMethod(
                    blurDrawable,
                    "setColor",
                    color
                )
            }
            return blurDrawable
        } catch (e: Throwable) {
            return null
        }
    }

    fun isBlurDrawable(drawable: Drawable?): Boolean {
        // 不够严谨，可以用
        if (drawable == null) {
            return false
        }
        val drawableClassName = drawable.javaClass.name
        return drawableClassName.contains("BackgroundBlurDrawable")
    }
}