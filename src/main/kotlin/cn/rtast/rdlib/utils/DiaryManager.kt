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
import cn.rtast.rdlib.data.DiaryContent
import cn.rtast.rdlib.data.DiaryDate
import cn.rtast.rdlib.exceptions.DiaryAlreadyExistsException
import cn.rtast.rdlib.exceptions.DiaryNotExistsException
import mu.two.KotlinLogging

class DiaryManager {

    private val remoteBasePath = "src/content/diary"
    private val diaryFileManager = FileManager()
    private val logger = KotlinLogging.logger(this::class.java.name)

    private fun createDiary(diaryDate: DiaryDate = getCurrentDate(), overwrite: Boolean = false) {
        val date = diaryDate.getDate()
        val tmplContent = DIARY_TEMPLATE.replace("_date", date)
        if (this.diaryFileManager.fileExists(date) && !overwrite) {
            throw DiaryAlreadyExistsException("This diary is already exists")
        } else if (this.diaryFileManager.fileExists(date) && overwrite) {
            logger.warn("This diary($date) will be overwrite to default template!")
            this.diaryFileManager.updateFile(date, tmplContent)
            return
        }
        logger.info("Created a new diary: $date")
        diaryFileManager.createFile(date, tmplContent)
    }

    fun deleteDiary(diaryDate: DiaryDate = getCurrentDate()) {
        val dateString = diaryDate.getDate()
        if (this.diaryFileManager.fileExists(dateString)) {
            this.diaryFileManager.deleteFile(dateString)
        } else {
            throw DiaryNotExistsException("Diary($dateString) is not exists")
        }
    }

    fun getDiaryContent(diaryDate: DiaryDate): String {
        val dateString = diaryDate.getDate()
        if (this.diaryFileManager.fileExists(dateString)) {
            val content = this.diaryFileManager.readFile(dateString)
            if (content != null) {
                return content
            }
        }
        throw DiaryNotExistsException("Diary($dateString) is not exists")
    }

    fun updateDiaryContent(diaryDate: DiaryDate, newContent: DiaryContent) {

    }

    fun appendDiaryContent(diaryDate: DiaryDate, newContent: DiaryContent) {

    }
}