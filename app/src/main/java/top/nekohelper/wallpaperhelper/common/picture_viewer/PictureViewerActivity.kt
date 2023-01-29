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

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import top.nekohelper.wallpaperhelper.BuildConfig
import top.nekohelper.wallpaperhelper.databases.Picture
import top.nekohelper.wallpaperhelper.databinding.ActivityPictureViewerBinding
import top.nekohelper.wallpaperhelper.utils.translateStatusBarAndNavigationBar

private const val PIC_INSTANCE = "pic_instance"
private const val TAG = "PictureViewerActivity"

class PictureViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictureViewerBinding
    private var downTouch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureViewerBinding.inflate(layoutInflater)

        translateStatusBarAndNavigationBar()

        // Make our nav bar placeholder's height equal nav bar's height
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.viewNavBarPlaceholder.apply {
                layoutParams = layoutParams.also {
                    it.height = systemBars.bottom
                }
            }
            return@setOnApplyWindowInsetsListener insets
        }

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
        fun switchActionPanelHide() {
            val previewActionPanelVisibility: Int
            val previewBackgroundAttr: Int
            val isPanelShowing  = binding.previewActionPanel.visibility == View.VISIBLE

            // Toggle current value
            if (isPanelShowing) {
                previewActionPanelVisibility = View.GONE
                previewBackgroundAttr = com.google.android.material.R.attr.colorOnSurface
            } else {
                previewActionPanelVisibility = View.VISIBLE
                previewBackgroundAttr = com.google.android.material.R.attr.colorSurface
            }

            // Transform picture background int to color
            val previewBkgColorValue = TypedValue().apply {
                theme.resolveAttribute(
                    previewBackgroundAttr,
                    this, true
                )
            }

            // Picture background
            binding.viewBackground.setBackgroundColor(previewBkgColorValue.data)

            // Action panel visibility
            binding.previewActionPanel.visibility = previewActionPanelVisibility
        }

        val previewImageView = binding.scaleIvPicViewer

        val picUri = picture.toUri()
        picUri?.apply {
            previewImageView.setImage(ImageSource.uri(this))
        }

        val actionPanelCtrlGestureListener = object : GestureDetector.SimpleOnGestureListener() {
            // With SubsamplingScaleImageView:
            // It is safe to override
            // onSingleTapUp, onSingleTapConfirmed, onLongPress and onDown
            // from the SimpleOnGestureListener class.
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                switchActionPanelHide()
                return super.onSingleTapConfirmed(e)
            }
        }
        val actionPanelCtrlGestureDetector = GestureDetector(this, actionPanelCtrlGestureListener)
        val imagePreviewOnTouchListener = View.OnTouchListener { v, event ->
            // Process view accessibility
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downTouch = true
                }

                MotionEvent.ACTION_UP -> if (downTouch) {
                    downTouch = false
                    v.performClick()
                }
            }

            return@OnTouchListener actionPanelCtrlGestureDetector.onTouchEvent(event!!)
        }
        val ivStateChangeListener = object : SubsamplingScaleImageView.OnStateChangedListener {
            @SuppressLint("SetTextI18n")
            override fun onScaleChanged(newScale: Float, origin: Int) {
                val scaleText = ((newScale * 1000).toInt() / 10f).toString() + "%"
                binding.btnScaleHint.text = scaleText
            }

            override fun onCenterChanged(newCenter: PointF?, origin: Int) {  }
        }

        val ivEventListener = object : SubsamplingScaleImageView.OnImageEventListener {
            override fun onReady() {
                val scaleText = ((previewImageView.scale * 1000).toInt() / 10f).toString() + "%"
                binding.btnScaleHint.text = scaleText
            }

            override fun onImageLoaded() {  }

            override fun onPreviewLoadError(e: Exception?) { /* todo: Show error msg to user */ }

            override fun onImageLoadError(e: Exception?) { /* todo: Show error msg to user */ }

            override fun onTileLoadError(e: Exception?) {  }

            override fun onPreviewReleased() {  }
        }

        previewImageView.apply {
            setDebug(BuildConfig.DEBUG)
            // maxScale = 1f
            orientation = SubsamplingScaleImageView.ORIENTATION_USE_EXIF
            setOnTouchListener(imagePreviewOnTouchListener)
            setOnStateChangedListener(ivStateChangeListener)
            setOnImageEventListener(ivEventListener)
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