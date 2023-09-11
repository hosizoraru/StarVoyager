package star.sky.voyager.hook.hooks.screenshot

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object SaveAsPng : HookRegister() {
    override fun init() = hasEnable("save_as_png") {
        val contentResolverCls =
            loadClass("android.content.ContentResolver")

        contentResolverCls.methodFinder()
            .filterByName("update")
            .filterByParamCount(4)
            .toList().createHooks {
                before { param ->
                    val contentValues = param.args[1] as ContentValues
                    var displayName = contentValues.getAsString("_display_name")
                    if (displayName != null && displayName.contains("Screenshot")) {
                        val ext = ".png"
                        displayName = displayName.replace(".png", "").replace(".jpg", "")
                            .replace(".webp", "") + ext
                        contentValues.put("_display_name", displayName)
                    }
                }
            }

        contentResolverCls.methodFinder()
            .filterByName("insert")
            .filterByParamTypes(Uri::class.java, ContentValues::class.java)
            .toList().createHooks {
                before { param ->
                    val imgUri = param.args[0] as Uri
                    val contentValues = param.args[1] as ContentValues
                    var displayName = contentValues.getAsString("_display_name")
                    if (MediaStore.Images.Media.EXTERNAL_CONTENT_URI == imgUri && displayName != null && displayName!!.contains(
                            "Screenshot"
                        )
                    ) {
                        val ext = ".png"
                        displayName = displayName!!.replace(".png", "").replace(".jpg", "")
                            .replace(".webp", "") + ext
                        contentValues.put("_display_name", displayName)
                    }
                }
            }

        loadClass("android.graphics.Bitmap").methodFinder()
            .filterByName("compress")
            .toList().createHooks {
                after { param ->
//                    Log.i("qwq!!!!: ${param.args[0]}")
//                    var quality = param.args[1] as Int
//                    if (quality != 100 || param.args[2] is ByteArrayOutputStream) return@after
                    val compress =
                        Bitmap.CompressFormat.PNG
//                    Log.i("qwq!!!!: ${param.args[1]}")
//                    quality = 75
                    param.args[0] = compress
//                    param.args[1] = quality
//                    Log.i("qwq!!!!: ${param.args[1]}")
//                    Log.i("qwq!!!!: ${param.args[0]}")
                }
            }

//        dexKitBridge.batchFindMethodsUsingStrings {
//            addQuery("qwq", setOf("context", "bitmap", "uri", "format"))
//            matchType = MatchType.FULL
//        }
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "context", "bitmap", "uri", "format"
                )
            }
        }.forEach {
//                (_, methods) ->
//            methods
//                it.map {
                val pngMethod = it.getMethodInstance(classLoader)
                (pngMethod.returnType == Boolean::class.java && pngMethod.paramCount == 7).apply {
                    pngMethod.createHook {
                        after { param ->
                            val compress =
                                Bitmap.CompressFormat.PNG
//                            Log.i("qwq!!!!: ${param.args[4]}")
                            param.args[4] = compress
//                            Log.i("qwq!!!!: ${param.args[4]}")
                        }
                    }
                }
            }
//        }

//        loadClass("com.miui.screenshot.u0.f\$a").methodFinder()
//            .filterByName("a")
//            .filterByParamCount(7)
//            .first().createHook {
//                after { param ->
////                    Log.i("qwq!!!!: ${param.args[4]}")
//                    val compress =
//                        Bitmap.CompressFormat.PNG
//                    param.args[4] = compress
////                    Log.i("qwq!!!!: ${param.args[4]}")
//                }
//            }

    }
}