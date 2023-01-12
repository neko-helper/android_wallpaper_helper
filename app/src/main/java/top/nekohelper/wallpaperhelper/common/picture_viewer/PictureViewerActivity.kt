/*
 *     Android wallpaper helper
 *     Copyright (C) 2023  gtf35
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

package top.nekohelper.wallpaperhelper.common.picture_viewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import top.nekohelper.wallpaperhelper.BuildConfig
import top.nekohelper.wallpaperhelper.databases.Picture
import top.nekohelper.wallpaperhelper.databinding.ActivityPictureViewerBinding
import top.nekohelper.wallpaperhelper.utils.translateStatusBar

private const val PIC_INSTANCE = "pic_instance"
private const val TAG = "PictureViewerActivity"

class PictureViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictureViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureViewerBinding.inflate(layoutInflater)
        translateStatusBar()
        setContentView(binding.root)

        val calledIntent = intent
        if (calledIntent == null) {
            Log.w(TAG, "onCreate: calledIntent is null, finish preview!")
            finish()
            return
        }

        val pictureJson = calledIntent.getStringExtra(PIC_INSTANCE)
        if (pictureJson == null) {
            Log.w(TAG, "onCreate: pictureJson is null, finish preview!")
            finish()
            return
        }

        val picture = Picture.fromJson(pictureJson)
        if (picture == null) {
            Log.w(TAG, "onCreate: picture is null, finish preview!")
            finish()
            return
        }

        prepImage(picture)
    }

    private fun prepImage(picture: Picture) {
        val previewImageView = binding.scaleIvPicViewer

        val picUri = picture.toUri()
        picUri?.apply {
            previewImageView.setDebug(BuildConfig.DEBUG)
            previewImageView.maxScale = 1f
            previewImageView.orientation = SubsamplingScaleImageView.ORIENTATION_USE_EXIF
            previewImageView.setImage(ImageSource.uri(this))
        }
    }

    companion object {
        fun comeOn(fromActivity: Activity, picture: Picture) {
            val comeOnIntent = Intent(fromActivity, PictureViewerActivity::class.java).apply {
                putExtra(PIC_INSTANCE, picture.toJson())
            }
            fromActivity.startActivity(comeOnIntent)
        }
    }
}