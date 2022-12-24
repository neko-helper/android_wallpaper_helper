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

package top.nekohelper.wallpaperhelper

import org.junit.Assert
import org.junit.Test
import top.nekohelper.wallpaperhelper.main.msg.ImportingMsgItem

class ImportingMsgItemTest {
    @Test
    fun valueSame_HashcodeSame() {
        val a = ImportingMsgItem(1, 10, msgID = 1)
        val b = ImportingMsgItem(1, 10, msgID = 1)
        val testNotPassMsg = "value same, but hashcode is not same"
        Assert.assertEquals(testNotPassMsg, a.hashCode(), b.hashCode())
    }

    @Test
    fun valueNotSame_HashcodeNotSame() {
        // allCount and finishedCount not participate in the calculation
        val a = ImportingMsgItem(1, 10, msgID = 1)
        val b = ImportingMsgItem(1, 10, msgID = 2)
        val testNotPassMsg = "msgID not same, but hashcode is same"
        Assert.assertNotEquals(testNotPassMsg, a.hashCode(), b.hashCode())
    }

    @Test
    fun valueSame_EqualsTrue() {
        val a = ImportingMsgItem(1, 10, msgID = 1)
        val b = ImportingMsgItem(1, 10, msgID = 1)
        Assert.assertEquals(a, b)
    }

    @Test
    fun valueNotSame_EqualsFalse() {
        val a = ImportingMsgItem(1, 10, msgID = 1)
        val b = ImportingMsgItem(2, 10, msgID = 1)
        var testNotPassMsg = "allCount not same, but equals is same"
        Assert.assertNotEquals(testNotPassMsg, a, b)

        val c = ImportingMsgItem(1, 10, msgID = 1)
        val d = ImportingMsgItem(1, 10, msgID = 2)
        testNotPassMsg = "msgID not same, but equals is same"
        Assert.assertNotEquals(testNotPassMsg, c, d)

        testNotPassMsg = "msgID and allCount not same, but equals is same"
        Assert.assertNotEquals(testNotPassMsg, b, d)
    }
}