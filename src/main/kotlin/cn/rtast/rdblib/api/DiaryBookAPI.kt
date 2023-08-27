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

package cn.rtast.rdblib.api

import cn.rtast.rdblib.entities.Content
import cn.rtast.rdblib.entities.commit.Commit
import cn.rtast.rdblib.entities.commit.Committer
import cn.rtast.rdblib.utils.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class DiaryBookAPI {
    companion object {
        const val TOKEN = "TOKEN HERE"
        private const val OWNER = "RTAkland"
        private const val REPO = "DiaryBook"
        private const val API_BASE_URL = "https://api.github.com"
        private const val COMMITTER_EMAIL = "rtakland@Outlook.com"
        private const val COMMITTER_USERNAME = "RTAkland"
    }

    private val contentTree = mutableListOf<Content>()


    private fun getAllContent(path: String) {
        val directoryContent = Http.get(
            "$API_BASE_URL/repos/$OWNER/$REPO/contents/$path", null, null
        ).body.string().fromArrayJson<List<ContentItem>>()
        directoryContent.forEach {
            if (it.type == ContentType.Directory.id) {
                this.getAllContent(it.path)
            } else {
                contentTree.add(Content(it.name, it.path, it.download_url))
            }
        }
    }

    fun getFileContent(path: String): Content {
        val fileContent = Http.get(
            "$API_BASE_URL/repos/$OWNER/$REPO/contents/$path", null, null
        ).body.string().fromJson<ContentItem>()
        return Content(fileContent.name, fileContent.path, fileContent.download_url)
    }

    fun getDirContent(): List<Content> {
        this.getAllContent("src/content/diary")
        return this.contentTree
    }

    fun updateFileContent(path: String, newContent: String) {
        val requestBody = Commit(
            "update file ${getCurrentDate()}", Committer(
                COMMITTER_USERNAME, COMMITTER_EMAIL
            ), newContent
        ).toJsonString().toRequestBody()

        Http.put("src/content/diary$path", requestBody, null)
    }

    fun deleteFile(path: String) {
        val requestBody = Commit(
            "update file ${getCurrentDate()}", Committer(
                COMMITTER_USERNAME, COMMITTER_EMAIL
            ), File("./$path").getSha()
        ).toJsonString().toRequestBody()

        Http.delete("src/content/diary/$path", requestBody, null)
    }

    enum class ContentType(val id: String) {
        Directory("dir"), File("file")
    }

    data class ContentItem(
        val type: String,
        val encoding: String,
        val size: Long,
        val name: String,
        val path: String,
        val download_url: String
    )

}