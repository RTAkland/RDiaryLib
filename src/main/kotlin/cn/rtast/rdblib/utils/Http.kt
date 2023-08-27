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

import cn.rtast.rdblib.DiaryManager
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

object Http {

    private val client = OkHttpClient()

    fun get(url: String, params: Map<String, String>?, headers: Map<String, String>?): Response {
        val urlWithParams = buildUrlWithParams(url, params)
        val request = buildRequest(urlWithParams, headers)

        return executeRequest(request)
    }

    fun post(url: String, requestBody: RequestBody, headers: Map<String, String>?): Response {
        val request = buildRequest(url, headers).post(requestBody)

        return executeRequest(request)
    }

    fun put(url: String, requestBody: RequestBody, headers: Map<String, String>?): Response {
        val request = buildRequest(url, headers).put(requestBody)

        return executeRequest(request)
    }

    fun delete(url: String, requestBody: RequestBody, headers: Map<String, String>?): Response {
        val request = buildRequest(url, headers)
            .delete(requestBody)

        return executeRequest(request)
    }

    private fun buildUrlWithParams(url: String, params: Map<String, String>?): String {
        if (params.isNullOrEmpty()) {
            return url
        }

        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()
        params.forEach { (key, value) ->
            urlBuilder?.addQueryParameter(key, value)
        }

        return urlBuilder?.build().toString()
    }

    private fun buildRequest(url: String, headers: Map<String, String>?): Request.Builder {
        val requestBuilder = Request.Builder().url(url)

        headers?.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }

        return requestBuilder
    }

    private fun executeRequest(request: Request.Builder): Response {
        request.addHeader("Accept", "application/vnd.github+json")
        request.addHeader("Authorization", "Bearer ${DiaryManager.githubToken}")
        return client.newCall(request.build()).execute()
    }
}