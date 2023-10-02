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

package cn.rtast.rdlib.data

class DiaryDate(private val year: Int, private val month: Int, private val day: Int) {
    fun getDate(slash: Boolean): String {
        return if (slash) "$year/$month/$day" else "$year-$month-$day"
    }

    fun getDate(): String {
        return "$year-$month-$day"
    }

    fun toIntArray(): IntArray {
        return listOf(year, month, day).toIntArray()
    }
}