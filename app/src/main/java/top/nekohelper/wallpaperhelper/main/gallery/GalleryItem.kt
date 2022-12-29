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

package top.nekohelper.wallpaperhelper.main.gallery

import top.nekohelper.wallpaperhelper.common.ISpanCounter
import top.nekohelper.wallpaperhelper.databases.Picture

data class GalleryItem(
    val pic: Picture,
): ISpanCounter {

    override fun hashCode(): Int {
        var code = 0
        code += GalleryItem::class.java.name.hashCode()
        code += pic.id.hashCode()
        code += pic.fileName.hashCode()
        code += pic.createTimestamp.hashCode()
        return code
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is GalleryItem) return false
        if (other.pic.id != pic.id) return false
        return true
    }

    override fun getSpanCount(): Int {
        return 1
    }
}
