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

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.nekohelper.wallpaperhelper.main.msg.ImportingMsgItem
import top.nekohelper.wallpaperhelper.databases.AppDatabase
import top.nekohelper.wallpaperhelper.databases.FileStorage
import top.nekohelper.wallpaperhelper.databases.Picture
import top.nekohelper.wallpaperhelper.utils.*
import java.io.File

@Suppress("unused")
private const val TAG = "MainVM"

class MainVM(private val app: Application) : AndroidViewModel(app) {
    private val mBaseFolder = AppPath.getPictureStoreFolder()
    private val mPicDao = AppDatabase.getInstance(app).pictureDao()

    // don't use mutable list, MutableLiveData doesn't known list itself inner change
    private val mImportingMsgListLiveData = MutableLiveData<List<ImportingMsgItem>>(listOf())
    val importingMsgListLiveData: LiveData<List<ImportingMsgItem>>
        get() = mImportingMsgListLiveData

    val galleryPicListLiveData = mPicDao.getAll()



    fun saveUrisToGalleryAsync(uris: List<Uri>) {
        CoroutineScope(Dispatchers.IO).launch {
            val msgID = Utils.getLongMsTimestamp()
            val allCount = uris.size
            var finishedCount = 0
            var isFinished = false

            fun updateList(isCleanList: Boolean = false) {
                val msgItem = ImportingMsgItem(
                    allCount = allCount,
                    finishedCount = finishedCount,
                    msgID = msgID,
                    isFinished = isFinished
                )
                val originalList = mImportingMsgListLiveData.value!!
                val listWithoutThisMsg = originalList.filterNot{ it.msgID == msgID }
                // use plus() to create a new list
                var addedList = listWithoutThisMsg.plus(msgItem)
                // if clean msg, change back to the original array that was not added
                if (isCleanList) addedList = listWithoutThisMsg
                mImportingMsgListLiveData.postValue(addedList)
            }

            for (uri in uris) {
                finishedCount += 1
                updateList()
                saveFileAndInsertDatabase(uri)
            }

            // Tell recycle view adapter to switch to finished state
            isFinished = true
            updateList()

            // High-performance devices will be imported quickly, easily overlooked,
            // and even considered as bugs, which will affect the experience.
            // Delay for one second to let the user see that the import has completed
            delay(3 * 1000)
            // remove msg item
            updateList(isCleanList = true)
        }
    }

    private suspend fun Uri.takePicPersistableUriPermission() = withContext(Dispatchers.IO) {
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        app.contentResolver.takePersistableUriPermission(this@takePicPersistableUriPermission, flag)
    }

    private suspend fun saveFileAndInsertDatabase(uri: Uri) = withContext(Dispatchers.IO) {
        // Let users have the ability to access the original image
        uri.takePicPersistableUriPermission()

        // Import too fast, delay it, only for test
        // delay(5 * 1000)
        val fileName = genFileName(uri)
        val targetFile = File(mBaseFolder, fileName)
        // Backup picture
        uri.copyTo(targetFile)
        saveToDatabase(targetFile, uri)
    }

    private suspend fun saveToDatabase(picFile: File, picUri: Uri) {
        val picUriStr = picUri.toString()
        val widthAndHeight = Utils.getPicWidthAndHeight(picFile)
        val width = widthAndHeight[0]
        val height = widthAndHeight[1]
        val picEntity = Picture(
            fileName = picFile.name,
            fileStorageFlag = FileStorage.EXTERNAL_STORAGE.flag,
            fileSize = picFile.length(),
            width = width,
            height = height,
            fileUri = picUriStr
        )
        mPicDao.insertPictures(picEntity)
    }

    private suspend fun genFileName(uri: Uri): String {
        val extensionName = uri.getMimeType(app)?: "?"
        val fileName = 10.randomString
        return "$fileName.$extensionName"
    }
}