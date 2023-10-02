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

import cn.rtast.rdlib.models.FileURI
import mu.two.KotlinLogging
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class FileManager(basePath: String = "./diary") {

    private val logger = KotlinLogging.logger(this::class.java.name)

    private val basePath: String

    init {
        this.basePath = basePath
        val baseDir = File(this.basePath)
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
    }

    fun createFile(fileURI: FileURI, content: String) {
        logger.debug("create file: ${fileURI.filename}")
        val filePath = "$basePath/${fileURI.path}/${fileURI.filename}.md"
        println(filePath)
        val file = File(filePath)

        if (file.exists()) {
            logger.info("文件已存在: $filePath")
        } else {
            FileWriter(file).use { writer ->
                writer.write(content)
            }
            logger.info("文件已创建: $filePath")
        }
    }

    fun deleteFile(fileURI: FileURI) {
        logger.debug("delete file: ${fileURI.filename}")
        val filePath = "$basePath/${fileURI.path}/${fileURI.filename}.md"
        val file = File(filePath)

        if (file.exists()) {
            file.delete()
            logger.info("文件已删除: $filePath")
        } else {
            logger.info("文件不存在: $filePath")
        }
    }

    fun readFile(fileURI: FileURI): String? {
        logger.debug("read file: ${fileURI.filename}")
        val filePath = "$basePath/${fileURI.path}/${fileURI.filename}.md"
        val file = File(filePath)

        if (file.exists()) {
            FileReader(file).use { reader ->
                val content = reader.readText()
                logger.info("读取文件内容成功: $content")
                return content
            }
        } else {
            logger.info("文件不存在: $filePath")
        }
        return null
    }

    fun updateFile(fileURI: FileURI, newContent: String) {
        logger.debug("update file: ${fileURI.filename}, content: $newContent")
        val filePath = "$basePath/${fileURI.path}/${fileURI.filename}.md"
        val file = File(filePath)

        if (file.exists()) {
            FileWriter(file).use { writer ->
                writer.write(newContent)
            }
            logger.info("文件已更新: $filePath")
        } else {
            logger.info("文件不存在: $filePath")
        }
    }

    fun fileExists(fileURI: FileURI): Boolean {
        logger.debug("check file status: ${fileURI.filename}")
        val filePath = "$basePath/${fileURI.path}/${fileURI.filename}.md"
        val file = File(filePath)
        val state = file.exists()
        logger.debug("file state: $state")
        return state
    }
}
