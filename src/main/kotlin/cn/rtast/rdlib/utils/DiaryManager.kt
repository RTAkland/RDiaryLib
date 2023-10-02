/*
 * Copyright 2023 RTAkland
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package cn.rtast.rdlib.utils

import cn.rtast.rdlib.DIARY_TEMPLATE
import cn.rtast.rdlib.RDiaryLib
import cn.rtast.rdlib.entities.RootContentEntity
import cn.rtast.rdlib.entities.RootContentEntityItem
import cn.rtast.rdlib.exceptions.DiaryAlreadyExistsException
import cn.rtast.rdlib.exceptions.DiaryNotExistsException
import cn.rtast.rdlib.models.DiaryDate
import cn.rtast.rdlib.models.FileURI
import com.google.gson.Gson
import mu.two.KotlinLogging

class DiaryManager {

    private val remoteBasePath = "src/content/diary"
    private val remoteBaseUrl =
        "https://api.github.com/repos/${RDiaryLib.owner}/${RDiaryLib.repo}/contents/$remoteBasePath"
    private val diaryFileManager = FileManager()
    private val logger = KotlinLogging.logger(this::class.java.name)

    fun createDiary(diaryDate: DiaryDate = getCurrentDate(), overwrite: Boolean = false) {
        val date = diaryDate.date
        val fileURI = FileURI(date, diaryDate)
        val tmplContent = DIARY_TEMPLATE.replace("_date", date)
        if (this.diaryFileManager.fileExists(fileURI) && !overwrite) {
            throw DiaryAlreadyExistsException("This diary is already exists")
        } else if (this.diaryFileManager.fileExists(fileURI) && overwrite) {
            logger.warn("This diary($date) will be overwrite to default template!")
            this.diaryFileManager.updateFile(fileURI, tmplContent)
            return
        }
        diaryFileManager.createFile(fileURI, tmplContent)
    }

    fun deleteDiary(diaryDate: DiaryDate = getCurrentDate()) {
        val dateString = diaryDate.date
        val fileURI = FileURI(dateString, diaryDate)
        if (this.diaryFileManager.fileExists(fileURI)) {
            this.diaryFileManager.deleteFile(fileURI)
        } else {
            throw DiaryNotExistsException("Diary($dateString) is not exists")
        }
    }

    fun getDiaryContent(diaryDate: DiaryDate = getCurrentDate()): String {
        val dateString = diaryDate.date
        val fileURI = FileURI(dateString, diaryDate)
        if (this.diaryFileManager.fileExists(fileURI)) {
            val content = this.diaryFileManager.readFile(fileURI)
            if (content != null) {
                return content
            }
        }
        throw DiaryNotExistsException("Diary($dateString) is not exists")
    }

    fun updateDiaryContent(diaryDate: DiaryDate = getCurrentDate(), newContent: String) {
        val dateString = diaryDate.date
        val fileURI = FileURI(dateString, diaryDate)
        if (this.diaryFileManager.fileExists(fileURI)) {
            this.diaryFileManager.updateFile(fileURI, newContent)
        } else {
            throw DiaryNotExistsException("Diary($dateString) is not exists")
        }
    }

    fun sync() {
        val response = Http.get(this.remoteBaseUrl).body.string().fromArrayJson<List<RootContentEntityItem>>()
        println(response)
    }
}