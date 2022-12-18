@file:Suppress("unused")

package top.nekohelper.wallpaperhelper.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat
import top.nekohelper.wallpaperhelper.WallpaperApp

// dp to px
val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

// dp to sp
val Int.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        WallpaperApp.appContext.resources.displayMetrics
    )

// px to dp
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

// get String
val Int.resStr: String
    get() = WallpaperApp.appContext.resources.getString(this)

// get Drawable
val Int.resDrawable: Drawable?
    get() = ResourcesCompat.getDrawable(WallpaperApp.appContext.resources, this, WallpaperApp.appContext.theme)

// get Color
val Int.resColor: Int
    get() = ResourcesCompat.getColor(WallpaperApp.appContext.resources, this, WallpaperApp.appContext.theme)

// get random string
val Int.randomString: String
    get() {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..this)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }