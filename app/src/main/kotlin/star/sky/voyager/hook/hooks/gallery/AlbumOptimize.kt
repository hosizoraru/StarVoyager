package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AlbumOptimize : HookRegister() {
    override fun init() = hasEnable("album_optimize") {
//        var modifiedResult: String?
        val albumManagerCls =
            loadClass("com.miui.gallery.provider.album.AlbumManager")
        val albumDataHelperCls =
            loadClass("com.miui.gallery.model.dto.utils.AlbumDataHelper")
        val sqlMethod =
            albumManagerCls.methodFinder()
                .filterByName("getQueryUnionScreenshotsRecordsAlbumSql").first()
//        val additionalPaths = "'Pictures/Screenshots', 'Movies/screenrecorder'"
        val qwq =
            " UNION SELECT _id, name, attributes, dateTaken, dateModified, sortInfo, extra, localFlag, serverId, localPath, realDateModified, serverTag, serverStatus, editedColumns, serverStatus,photoCount, size, sortBy,coverId, coverSyncState, coverSize, coverPath, coverSha1,is_manual_set_cover FROM ( SELECT 2147483645 AS _id, 'SCREENSHOTS OR RECORDERS' AS name, (SELECT attributes FROM album WHERE localPath COLLATE NOCASE IN ('DCIM/Screenshots')) AS attributes, 996 AS dateTaken, 996 AS dateModified, 0 AS sortBy, '996' AS sortInfo, NULL AS extra, 0 AS localFlag, 'custom' AS serverStatus, -2147483645 AS serverId, 0 AS realDateModified, NULL AS serverTag, NULL AS editedColumns, NULL AS localPath, _id AS coverId,  CASE WHEN localFlag = 0  THEN 0 WHEN localFlag IN (5, 6, 9) THEN 1 ELSE 3 END  AS coverSyncState, sha1 AS coverSha1, size AS coverSize, ( CASE WHEN (microthumbfile NOT NULL and microthumbfile != '') THEN microthumbfile WHEN (thumbnailFile NOT NULL and thumbnailFile != '') THEN thumbnailFile ELSE localFile END ) AS coverPath, 0 AS is_manual_set_cover, max( mixedDateTime ) AS latest_photo ,count(_id) AS photoCount, sum(size) AS size FROM ( SELECT _id,localFlag,localFile,thumbnailFile,microthumbfile,localGroupId,size,mixedDateTime,dateTaken,dateModified,serverType,sha1,serverStatus,creatorId FROM cloud WHERE (localFlag IS NULL OR localFlag NOT IN (11, 0, -1, 2, 15) OR (localFlag=0 AND (serverStatus='custom' OR serverStatus = 'recovery'))) AND (localGroupId IN (SELECT _id FROM album WHERE localPath COLLATE NOCASE IN ('DCIM/Screenshots', 'DCIM/screenrecorder', 'Pictures/Screenshots', 'Movies/screenrecorder')))) )"

//        sqlMethod.createHook {
//            after { param ->
//                val result = param.result as String
//                modifiedResult = result.replace(
//                    "('DCIM/Screenshots', 'DCIM/screenrecorder')",
//                    "('DCIM/Screenshots', 'DCIM/screenrecorder' ,$additionalPaths)"
//                )
//                Log.i("\"$modifiedResult\"")
//            }
//        }

        sqlMethod.createHook {
            returnConstant(qwq)
        }

//        albumManagerCls.methodFinder()
//            .filterByName("getQueryVirtualTable")
//            .first().createHook {
//                after {
//                    Log.i("qwq!!!: ${it.result}")
//                }
//            }

//        albumDataHelperCls.methodFinder().filter {
//            name in setOf("getScreenshotsLocalPath", "getScreenRecorderLocalPath")
//        }.toList().createHooks {
//            after {
//                Log.i("qwq!!!: ${it.result}")
//            }
//        }

        albumDataHelperCls.methodFinder()
            .filterByName("getScreenshotsLocalPath")
            .first().createHook {
                returnConstant("Pictures/Screenshots")
            }

        albumDataHelperCls.methodFinder()
            .filterByName("getScreenRecorderLocalPath")
            .first().createHook {
                returnConstant("Movies/ScreenRecorder")
            }
    }
}