package com.example.panda.api

import com.example.panda.models.chat.ChatModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("/v1/completions")
    suspend fun generatChat(
        @Header("Content-Type") contentType : String,
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ) : ChatModel
}