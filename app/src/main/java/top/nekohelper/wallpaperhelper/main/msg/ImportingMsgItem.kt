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

package top.nekohelper.wallpaperhelper.main.msg

import top.nekohelper.wallpaperhelper.common.Constants
import top.nekohelper.wallpaperhelper.common.ISpanCounter
import kotlin.random.Random

@Suppress("unused")
data class ImportingMsgItem(
    // Business value
    val allCount: Int,
    val finishedCount: Int,
    val isFinished: Boolean = false,
    // RecyclerView item id , used to calculate stableID
    val msgID: Long = Random.nextInt(0, 10000).toLong()
): ISpanCounter {

    companion object {
        const val MSG_IMPORTANCE_LEVEL = 1000
    }

    override fun hashCode(): Int {
        var code = 0
        code += ImportingMsgItem::class.java.name.hashCode()
        // Can't calculate with allCount,
        // cause msgID(1) + allCount(2) maybe equals msgID(2) + allCount(1), this can't be accept
        // By the way, msgID is can't duplicate, so calculate msgID is enough
        // code += allCount.hashCode()
        code += msgID.hashCode()
        return code
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is ImportingMsgItem) return false
        if (other.allCount != allCount) return false
        if (other.isFinished != isFinished) return false
        if (other.msgID != msgID) return false
        return true
    }

    override fun getSpanCount(): Int {
        return Constants.GALLERY_SPAN
    }
}