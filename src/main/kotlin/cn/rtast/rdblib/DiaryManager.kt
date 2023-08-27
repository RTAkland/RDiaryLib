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

package cn.rtast.rdblib

import cn.rtast.rdblib.api.DiaryBookAPI
import cn.rtast.rdblib.exceptions.DiaryExistsException
import cn.rtast.rdblib.exceptions.DiaryNotFoundException
import cn.rtast.rdblib.utils.format
import java.io.File
import java.util.*

class DiaryManager(token: String) {
    companion object {
        lateinit var githubToken: String
    }

    init {
        githubToken = token
    }

    private fun exists(path: String): Boolean {
        return File("./$path").exists()
    }

    fun newDiary(date: Date): File {
        val formatted = date.format()
        val path = "src/content/diary/${formatted.year}/${formatted.month}/${formatted.full}"
        if (!this.exists(path)) {
            val file = File(path)
            file.createNewFile()
            file.writeText(
                "---\n" + "title: \"${formatted.full}\"\n" + "description: \"${formatted.full}\"\n" + "pubDate: ${formatted.full}\n" + "---\n"
            )
            return file
        } else {
            throw DiaryExistsException("Diary is exists already.")
        }
    }

    fun updateDiary(date: Date, newContent: String): File {
        val formatted = date.format()
        val path = "src/content/diary/${formatted.year}/${formatted.month}/${formatted.full}"
        if (!this.exists(path)) {
            throw DiaryNotFoundException("This diary is not exists, ues newDiary() to init it.")
        } else {
            val file = File("./$path")
            file.writeText(newContent)
            DiaryBookAPI().updateFileContent(path, newContent)
            return file
        }
    }

    fun deleteDiary(date: Date) {
        val formatted = date.format()
        val path = "src/content/diary/${formatted.year}/${formatted.month}/${formatted.full}"
        if (!this.exists(path)) {
            throw DiaryExistsException("This diary is not exists, use newDiary() to init it.")
        } else {
            File("./$path").delete()
            DiaryBookAPI().deleteFile(path)
        }
    }

    data class DateModel(
        val year: Int, val month: Int, val full: String
    )
}