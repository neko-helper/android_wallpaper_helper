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

package top.nekohelper.wallpaperhelper.main.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.drakeet.multitype.ItemViewBinder
import top.nekohelper.wallpaperhelper.R

private const val TAG = "GalleryViewDelegate"

class GalleryViewDelegate(

    private val glideRequestManager: RequestManager,
    private val onItemClickListener: (item: GalleryItem) -> Unit
    ): ItemViewBinder<GalleryItem, GalleryViewDelegate.ViewHolder>() {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val picIV: AppCompatImageView = itemView.findViewById(R.id.iv_gallery_pic)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: GalleryItem) {
        Log.d(TAG, "onBindViewHolder: load pic to ImageView: $item")
        val picFile = item.pic.toFile()
        Log.d(TAG, "onBindViewHolder: pic path is ${picFile?.absolutePath}")
        glideRequestManager
            .load(picFile)
            .centerCrop()
            .into(holder.picIV)
        holder.picIV.setOnClickListener { onItemClickListener(item) }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_gallery_pic, parent, false))
    }

    override fun getItemId(item: GalleryItem): Long {
        return item.hashCode().toLong()
    }
}