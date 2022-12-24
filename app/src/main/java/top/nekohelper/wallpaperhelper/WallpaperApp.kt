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

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WallpaperApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        appContext = this
        FlipperUtils.prep(this)
    }

    companion object {
        // The lifecycle of this variable is as long as the life cycle of the application
        // so it will not cause memory leaks
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
    }
}