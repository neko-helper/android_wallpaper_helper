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

import androidx.lifecycle.LiveData
import androidx.room.*

@Suppress("unused")
@Dao
interface PictureDao {
    @Query("SELECT * FROM pictures")
    fun getAll(): LiveData<List<Picture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictures(vararg pictures: Picture)

    @Update
    suspend fun updatePictures(vararg pictures: Picture)

    @Query("DELETE FROM pictures WHERE id=:pictureId")
    suspend fun deletePictures(vararg pictureId: Int)

    @Delete
    suspend fun deletePictures(vararg user: Picture): Int
}
