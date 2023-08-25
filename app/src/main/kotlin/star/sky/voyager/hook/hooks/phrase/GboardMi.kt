package star.sky.voyager.hook.hooks.phrase

import android.annotation.SuppressLint
import com.github.kyuubiran.ezxhelper.ClassUtils.getStaticObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object GboardMi : HookRegister() {
    @SuppressLint("UseValueOf")
    override fun init() = hasEnable("mi_gboard") {
        val inputMethodBottomManager =
            loadClass("com.miui.inputmethod.InputMethodBottomManager")
        val sImeMinVersionSupport =
            getStaticObjectOrNullAs<HashMap<String, Integer>>(
                inputMethodBottomManager,
                "sImeMinVersionSupport"
            )
        val sImeLastMiui10Version =
            getStaticObjectOrNullAs<HashMap<String, Integer>>(
                inputMethodBottomManager,
                "sImeLastMiui10Version"
            )
//        Log.i("sImeMinVersionSupport1: $sImeMinVersionSupport")
//        Log.i("sImeLastMiui10Version1: $sImeLastMiui10Version")
        sImeMinVersionSupport?.put("com.google.android.inputmethod.latin", Integer(1000))
        sImeLastMiui10Version?.put("com.google.android.inputmethod.latin", Integer(1000))
//        Log.i("sImeMinVersionSupport2: $sImeMinVersionSupport")
//        Log.i("sImeLastMiui10Version2: $sImeLastMiui10Version")
        setStaticObject(inputMethodBottomManager, "sIsImeSupport", 1)
    }
}