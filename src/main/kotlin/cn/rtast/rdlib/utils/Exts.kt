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

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.format(slash: Boolean): String {
    val pattern = if (slash) "yyyy/MM/dd" else "yyyy-MM-dd"
    return this.format(DateTimeFormatter.ofPattern(pattern))
}