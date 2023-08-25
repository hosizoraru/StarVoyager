package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.getStaticObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MiGboard : HookRegister() {
    override fun init() = hasEnable("mi_gboard") {
        val availableVirtualKeyboardFragmentCls =
            loadClass("com.android.settings.inputmethod.AvailableVirtualKeyboardFragment")
        val inputMethodFunctionSelectUtilsCls =
            loadClass("com.android.settings.inputmethod.InputMethodFunctionSelectUtils")
        val sCustomIme =
            getStaticObjectOrNullAs<List<String>>(
                inputMethodFunctionSelectUtilsCls,
                "sCustomIme"
            ) as ArrayList<String>
//        Log.i("sCustomIme1: $sCustomIme")
        sCustomIme.add("com.google.android.inputmethod.latin")
//        Log.i("sCustomIme2: $sCustomIme")

        availableVirtualKeyboardFragmentCls.methodFinder()
            .filterByName("updateInputMethodPreferenceViews")
            .first().createHook {
                before { param ->
                    val fragment = param.thisObject as Any
                    val field =
                        availableVirtualKeyboardFragmentCls.getDeclaredField("mCustomizedInputMethod")
                    field.isAccessible = true
                    val customizedInputMethod = field.get(fragment) as MutableList<*>
                    val inputMethodList = listOf("com.google.android.inputmethod.latin")
                    val newList =
                        ArrayList<String>(customizedInputMethod.size + inputMethodList.size)
                    newList.addAll(customizedInputMethod.filterIsInstance<String>())
                    newList.addAll(inputMethodList)
                    field.set(fragment, newList)
                }
            }
    }
}
//        loadClass("com.android.settings.inputmethod.AvailableVirtualKeyboardFragment").declaredFields.first { field ->
//            field.isPrivate && field.type.name == "java.util.List" && field.name == "mCustomizedInputMethod"
//        }.apply { isAccessible = true }