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
class UtilsTest {
    @Test
    fun getIntSTimestamp_ReturnSecond() {
        val before = Utils.getIntSTimestamp()
        Thread.sleep(1000L)
        val now = Utils.getIntSTimestamp()
        val diff = now - before
        Assert.assertFalse("TOOK LONGER THAN EXPECTED 1:$diff", diff > 1)
    }
}