package star.sky.voyager.activity.pages

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMMenuPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.hook.PACKAGE_NAME_HOOKED
import star.sky.voyager.utils.key.BackupUtils
import star.sky.voyager.utils.yife.Terminal

@BMMenuPage("Menu")
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
                        Terminal.exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
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
                                if (it != "android") Terminal.exec("killall $it")
                            }
                            Toast.makeText(
                                activity,
                                getString(R.string.finished),
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        } catch (_: Throwable) {
                            Toast.makeText(
                                activity,
                                getString(R.string.su_permission),
                                Toast.LENGTH_LONG
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
                            Terminal.exec("killall com.android.systemui")
                            Toast.makeText(
                                activity,
                                getString(R.string.finished),
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        } catch (_: Throwable) {
                            Toast.makeText(
                                activity,
                                getString(R.string.su_permission),
                                Toast.LENGTH_LONG
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
                            Terminal.exec("killall com.miui.home")
                            Toast.makeText(
                                activity,
                                getString(R.string.finished),
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        } catch (_: Throwable) {
                            Toast.makeText(
                                activity,
                                getString(R.string.su_permission),
                                Toast.LENGTH_LONG
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
                    BackupUtils.backup(
                        activity,
                        activity.createDeviceProtectedStorageContext()
                            .getSharedPreferences("voyager_config", Context.MODE_WORLD_READABLE)
                    )
                })
        )

        TextSummaryWithArrow(TextSummaryV(textId = R.string.recovery, onClickListener = {
            BackupUtils.recovery(
                activity,
                activity.createDeviceProtectedStorageContext()
                    .getSharedPreferences("voyager_config", Context.MODE_WORLD_READABLE)
            )
        }))

        TextWithArrow(TextV(textId = R.string.ResetModule, onClickListener = {
            MIUIDialog(activity) {
                setTitle(R.string.ResetModuleDialog)
                setMessage(R.string.ResetModuleDialogTips)
                setLButton(R.string.done) {
                    activity.getSharedPreferences("voyager_config", Activity.MODE_WORLD_READABLE)
                        .edit().clear().apply()
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.ResetSuccess),
                        Toast.LENGTH_LONG
                    ).show()
                }
                setRButton(R.string.cancel)
                finally { dismiss() }
            }.show()
        }))
    }
}