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

import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import java.io.File


object Utils {
    fun getIntSTimestamp(): Int {
        return (System.currentTimeMillis() / 1000L).toInt()
    }

    fun getLongMsTimestamp(): Long {
        return System.currentTimeMillis()
    }

    fun getPicWidthAndHeight(picFile: File): IntArray {
        val options = Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(picFile.absolutePath, options)
        return intArrayOf(options.outWidth, options.outHeight)
    }
}