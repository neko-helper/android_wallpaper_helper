package top.nekohelper.wallpaperhelper.common.views


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


class UseWidthAsHeightImageView(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, widthSpec)
    }
}