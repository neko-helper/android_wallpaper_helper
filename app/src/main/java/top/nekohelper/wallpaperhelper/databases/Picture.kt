/*
 *     Android wallpaper helper
 *     Copyright (C) 2022  gtf35
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.nekohelper.wallpaperhelper.databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import top.nekohelper.wallpaperhelper.utils.AppPath
import top.nekohelper.wallpaperhelper.utils.Utils
import java.io.File

@Entity(tableName = "pictures")
data class Picture(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "protocol_ver") val protocolVer: Int = 0,
    @ColumnInfo(name = "display_name") val displayName: String? = null,
    @ColumnInfo(name = "create_timestamp") val createTimestamp: Int = Utils.getIntSTimestamp(),
    @ColumnInfo(name = "file_path") val filePath: String? = null,
    @ColumnInfo(name = "file_name") val fileName: String,
    @ColumnInfo(name = "file_storage_flag") val fileStorageFlag: String,
    @ColumnInfo(name = "file_size") val fileSize: Long,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
) {

    fun toFile(): File? {
        if (this.fileStorageFlag == FileStorage.EXTERNAL_STORAGE.flag) {
            val folder = AppPath.getPictureStoreFolder()
            return File(folder, fileName)
        }
        return null
    }
}

enum class FileStorage(val flag: String) {
    EXTERNAL_STORAGE("external"),
}