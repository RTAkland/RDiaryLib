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

package cn.rtast.rdblib.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*

fun String.removeFirstAndLastChar(): String {
    return this.substring(1, this.length - 1)
}

fun Any.toJsonString(): String {
    // Any data class to json string
    return Gson().toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    // String.fromJson<Example>()
    return Gson().fromJson(this, T::class.java)
}

inline fun <reified T> String.fromArrayJson(): T {
    return Gson().fromJson(this, object : TypeToken<T>() {}.type)
}

fun String.decode(): String {
    return Base64.getDecoder().decode(this).decodeToString()
}

fun ByteArray.decode(): String {
    return Base64.getDecoder().decode(this).decodeToString()
}

fun File.getSha(): String {
    val digest = MessageDigest.getInstance("SHA-1")
    val inputStream = FileInputStream(this)
    val buffer = ByteArray(8192)
    var bytesRead = inputStream.read(buffer)

    while (bytesRead != -1) {
        digest.update(buffer, 0, bytesRead)
        bytesRead = inputStream.read(buffer)
    }

    inputStream.close()

    val shaBytes = digest.digest()
    val builder = StringBuilder()

    for (byte in shaBytes) {
        builder.append(String.format("%02x", byte))
    }

    return builder.toString()
}