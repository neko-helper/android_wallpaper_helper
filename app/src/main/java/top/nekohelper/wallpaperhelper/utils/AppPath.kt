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

package top.nekohelper.wallpaperhelper.utils

import android.content.Context
import top.nekohelper.wallpaperhelper.R
import top.nekohelper.wallpaperhelper.WallpaperApp
import java.io.File

@Suppress("MemberVisibilityCanBePrivate")
object AppPath {
    fun getPictureStoreFolder(context: Context = WallpaperApp.appContext): File {
        val folderName = R.string.gallery_folder_name.resStr
        return File(context.getExternalFilesDir(""), "$folderName/").apply {
            if (!exists()) mkdirs()
        }
    }
}