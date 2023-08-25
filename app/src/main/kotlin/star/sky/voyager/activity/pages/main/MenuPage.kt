package star.sky.voyager.activity.pages.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.annotation.BMMenuPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.utils.init.PackageNameHook.PACKAGE_NAME_HOOKED
import star.sky.voyager.utils.key.BackupUtils.backup
import star.sky.voyager.utils.key.BackupUtils.recovery
import star.sky.voyager.utils.yife.Terminal.exec
import star.sky.voyager.utils.yife.XSharedPreferences.prefFileName

@SuppressLint("NonConstantResourceId")
@BMMenuPage(titleId = R.string.menu)
//@BMMenuPage("Menu")
class MenuPage : BasePage() {
    @SuppressLint("WorldReadableFiles")
    override fun onCreate() {
        TitleText(textId = R.string.reboot)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.reboot_system
            ) {
                MIUIDialog(activity) {
                    setTitle(R.string.warning)
                    setMessage(R.string.reboot_system_tips)
                    setLButton(R.string.cancel) {
                        dismiss()
                    }
                    setRButton(R.string.done) {
                        exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
                    }
                }.show()
            }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.restart_all_scope
            ) {
                MIUIDialog(activity) {
                    setTitle(R.string.warning)
                    setMessage(R.string.restart_all_scope_tips)
                    setLButton(R.string.cancel) {
                        dismiss()
                    }
                    setRButton(R.string.done) {
                        try {
                            PACKAGE_NAME_HOOKED.forEach {
                                if (it != "android") exec("killall $it")
                            }
                            makeText(
                                activity,
                                getString(R.string.finished),
                                LENGTH_SHORT
                            ).show()
                            dismiss()
                        } catch (_: Throwable) {
                            makeText(
                                activity,
                                getString(R.string.su_permission),
                                LENGTH_LONG
                            ).show()
                            dismiss()
                        }
                    }
                }.show()
            }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.restart_scope_systemui
            ) {
                MIUIDialog(activity) {
                    setTitle(R.string.warning)
                    setMessage(R.string.restart_scope_systemui_tips)
                    setLButton(R.string.cancel) {
                        dismiss()
                    }
                    setRButton(R.string.done) {
                        try {
                            exec("killall com.android.systemui")
                            makeText(
                                activity,
                                getString(R.string.finished),
                                LENGTH_SHORT
                            ).show()
                            dismiss()
                        } catch (_: Throwable) {
                            makeText(
                                activity,
                                getString(R.string.su_permission),
                                LENGTH_LONG
                            ).show()
                            dismiss()
                        }
                    }
                }.show()
            }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.restart_scope_miui_home
            ) {
                MIUIDialog(activity) {
                    setTitle(R.string.warning)
                    setMessage(R.string.restart_scope_miui_home_tips)
                    setLButton(R.string.cancel) {
                        dismiss()
                    }
                    setRButton(R.string.done) {
                        try {
                            exec("killall com.miui.home")
                            makeText(
                                activity,
                                getString(R.string.finished),
                                LENGTH_SHORT
                            ).show()
                            dismiss()
                        } catch (_: Throwable) {
                            makeText(
                                activity,
                                getString(R.string.su_permission),
                                LENGTH_LONG
                            ).show()
                            dismiss()
                        }
                    }
                }.show()
            }
        )
        Line()
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.backup, onClickListener = {
                    backup(
                        activity,
                        activity.createDeviceProtectedStorageContext()
                            .getSharedPreferences(prefFileName, Context.MODE_WORLD_READABLE)
                    )
                })
        )

        TextSummaryWithArrow(TextSummaryV(textId = R.string.recovery, onClickListener = {
            recovery(
                activity,
                activity.createDeviceProtectedStorageContext()
                    .getSharedPreferences(prefFileName, Context.MODE_WORLD_READABLE)
            )
        }))

        TextWithArrow(TextV(textId = R.string.ResetModule, onClickListener = {
            MIUIDialog(activity) {
                setTitle(R.string.ResetModuleDialog)
                setMessage(R.string.ResetModuleDialogTips)
                setLButton(R.string.done) {
                    activity.getSharedPreferences(prefFileName, Activity.MODE_WORLD_READABLE)
                        .edit().clear().apply()
                    makeText(
                        activity,
                        activity.getString(R.string.ResetSuccess),
                        LENGTH_LONG
                    ).show()
                }
                setRButton(R.string.cancel)
                finally { dismiss() }
            }.show()
        }))
    }
}