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

package top.nekohelper.wallpaperhelper.common.msg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import top.nekohelper.wallpaperhelper.R
import top.nekohelper.wallpaperhelper.utils.resStr

class ImportingMsgViewDelegate: ItemViewBinder<ImportingMsgItem, ImportingMsgViewDelegate.ViewHolder>()  {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val titleTV: MaterialTextView = itemView.findViewById(R.id.tv_importing_pic_title)
        val msgTV: MaterialTextView = itemView.findViewById(R.id.tv_importing_pic_msg)
        val circularProgressIndicator: CircularProgressIndicator = itemView.findViewById(R.id.progress_circular)
        val importFinishedIV: AppCompatImageView = itemView.findViewById(R.id.iv_import_finished)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ImportingMsgItem) {
        // Toggle completed and loading states
        if (item.isFinished) {
            // Finished work, hide progress and show finished logo
            holder.circularProgressIndicator.alpha = 0f
            holder.importFinishedIV.alpha = 1f
            
            // Show complete result
            val msg = String.format(R.string.import_finished_all_count.resStr, item.finishedCount, item.allCount)
            holder.titleTV.text = R.string.import_finished.resStr
            holder.msgTV.text = msg
        } else {
            // Working, show progress and hide finished logo
            holder.circularProgressIndicator.alpha = 1f
            holder.importFinishedIV.alpha = 0f

            // Update msg count
            val msg = String.format(R.string.finished_count_and_all_count.resStr, item.finishedCount, item.allCount)
            holder.titleTV.text = R.string.is_importing_pic.resStr
            holder.msgTV.text = msg
        }

        if (item.allCount > 0 && item.allCount >= item.finishedCount) {
            // Set right progress to ProgressIndicator
            val progressInt = item.finishedCount * 100 / item.allCount
            holder.circularProgressIndicator.setProgressCompat(progressInt, true)
        } else {
            // If progress cant accept, set back to indeterminate mode
            holder.circularProgressIndicator.isIndeterminate = true
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_msg_importing_pic, parent, false))
    }

    override fun getItemId(item: ImportingMsgItem): Long {
        return item.hashCode().toLong()
    }
}