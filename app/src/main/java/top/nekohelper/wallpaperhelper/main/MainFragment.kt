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

package top.nekohelper.wallpaperhelper.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.drakeet.multitype.MultiTypeAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import top.nekohelper.wallpaperhelper.R
import top.nekohelper.wallpaperhelper.common.msg.ImportingMsgItem
import top.nekohelper.wallpaperhelper.common.msg.ImportingMsgViewDelegate
import top.nekohelper.wallpaperhelper.databinding.FragmentMainBinding
import top.nekohelper.wallpaperhelper.utils.resStr

/**
 * Main Screen.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private val mMainVM: MainVM by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val mAdapter = MultiTypeAdapter()
    private val mAllItems = ArrayList<Any>()
    private val mImportingMsgItems = ArrayList<ImportingMsgItem>()

    // Photo picker activity result launcher
    // See: https://developer.android.com/training/data-storage/shared/photopicker
    private val _pickMultipleMediaRegistry = ActivityResultContracts.PickMultipleVisualMedia()
    private val mPickMultipleMedia = registerForActivityResult(_pickMultipleMediaRegistry) { uris ->
            if (uris.isNotEmpty()) {
                mMainVM.saveUrisToGalleryAsync(uris)
            } else {
                showSnack(R.string.import_cancel.resStr)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepRecycleView()
        prepImportPicFeature()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectPic() {
        mPickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showSnack(msg: String, @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(mBinding.root, msg, duration).show()
    }

    private fun prepImportPicFeature() {
        mBinding.importPicFab.setOnClickListener { selectPic() }
        val importingMsgListObserver = Observer<List<ImportingMsgItem>> { newImportingMsgList ->
            mImportingMsgItems.clear()
            val sortedList = newImportingMsgList.sortedByDescending { it.msgID }
            mImportingMsgItems.addAll(sortedList)
            syncRecycleViewItems()
        }
        mMainVM.importingMsgListLiveData.observe(viewLifecycleOwner, importingMsgListObserver)
    }

    private fun prepRecycleView() {
        mAdapter.apply {
            setHasStableIds(true)
            mBinding.recMainFragment.adapter = this
            register(ImportingMsgViewDelegate())
        }
        syncRecycleViewItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun syncRecycleViewItems() {
        mAllItems.clear()
        mAllItems.addAll(mImportingMsgItems)
        mAdapter.items = mAllItems
        mAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MainFragment.
         */
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}