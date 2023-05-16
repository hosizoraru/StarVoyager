package star.sky.voyager.utils.key

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

object BackupUtils {
    const val CREATE_DOCUMENT_CODE = 255774
    const val OPEN_DOCUMENT_CODE = 277451

    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SimpleDateFormat")
    fun backup(activity: Activity, sp: SharedPreferences) {
        sharedPreferences = sp
        saveFile(activity, "Voyager-${SimpleDateFormat("yyMMdd_HHmmss").format(System.currentTimeMillis())}.json")
    }

    fun recovery(activity: Activity, sp: SharedPreferences) {
        sharedPreferences = sp
        openFile(activity)
    }

    private fun openFile(activity: Activity) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/json"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        activity.startActivityForResult(intent, OPEN_DOCUMENT_CODE)
    }


    private fun saveFile(activity: Activity, fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/json"
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        activity.startActivityForResult(intent, CREATE_DOCUMENT_CODE)
    }

    fun handleReadDocument(activity: Activity, data: Uri?) {
        val edit = sharedPreferences.edit()
        val uri = data ?: return
        try {
            activity.contentResolver.openInputStream(uri)?.let { loadFile ->
                BufferedReader(InputStreamReader(loadFile)).apply {
                    val sb = StringBuffer()
                    var line = readLine()
                    while (line != null) {
                        sb.append(line)
                        line = readLine()
                    }
                    val read = sb.toString()
                    JSONObject(read).apply {
                        val key = keys()
                        while (key.hasNext()) {
                            val keys = key.next()
                            when (val value = get(keys)) {
                                is String -> {
                                    if (value.startsWith("Float:")) {
                                        edit.putFloat(keys, value.substring(value.indexOf("Float:")).toFloat() / 1000)
                                    } else {
                                        edit.putString(keys, value)
                                    }
                                }

                                is Boolean -> edit.putBoolean(keys, value)
                                is Int -> edit.putInt(keys, value)
                            }
                        }
                    }
                    close()
                }
            }
            edit.apply()
            Toast.makeText(activity, "Load successfully", Toast.LENGTH_LONG).show()
        } catch (e: Throwable) {
            Toast.makeText(activity, "Load failed\n$e", Toast.LENGTH_LONG).show()
        }
    }

    fun handleCreateDocument(activity: Activity, data: Uri?) {
        val uri = data ?: return
        try {
            activity.contentResolver.openOutputStream(uri)?.let { saveFile ->
                BufferedWriter(OutputStreamWriter(saveFile)).apply {
                    write(JSONObject().also {
                        for (entry: Map.Entry<String, *> in sharedPreferences.all) {
                            when (entry.value) {
                                Float -> it.put(entry.key, "Float:" + (entry.value as Float * 1000).toInt().toString())
                                else -> it.put(entry.key, entry.value)
                            }
                        }
                    }.toString())
                    close()
                }
            }
            Toast.makeText(activity, "Save successfully", Toast.LENGTH_LONG).show()
        } catch (e: Throwable) {
            Toast.makeText(activity, "Save failed\n$e", Toast.LENGTH_LONG).show()
        }
    }

}