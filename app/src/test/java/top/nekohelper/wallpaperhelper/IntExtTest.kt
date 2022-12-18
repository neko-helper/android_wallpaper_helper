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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import top.nekohelper.wallpaperhelper.utils.*

@RunWith(RobolectricTestRunner::class)
@Config(application = WallpaperApp::class)
class IntExtTest {
    @Test
    fun pxExt_ReturnAcceptableValue() {
        Assert.assertNotEquals(4.px, 0)
    }

    @Test
    fun spExt_ReturnAcceptableValue() {
        Assert.assertNotEquals(4.sp, 0)
    }

    @Test
    fun dpExt_ReturnAcceptableValue() {
        Assert.assertNotEquals(4.dp, 0)
    }

    @Test
    fun resStrExt_ReturnAcceptableValue() {
        Assert.assertEquals(android.R.string.ok.resStr, "OK")
    }

    @Test
    fun resDrawableExt_ReturnAcceptableValue() {
        Assert.assertNotEquals(android.R.drawable.ic_lock_power_off.resDrawable, null)
    }

    @Test
    fun resColorExt_ReturnAcceptableValue() {
        Assert.assertEquals(android.R.color.black.resColor, -16777216)
    }

    @Test
    fun randomStringExt_ReturnAcceptableValue() {
        Assert.assertNotEquals(10.randomString, null)
        Assert.assertEquals(10.randomString.length, 10)
    }
}