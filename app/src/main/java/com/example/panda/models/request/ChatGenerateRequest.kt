package com.example.panda.models.request

data class ChatGenerateRequest(
    val max_tokens: Int,
    val model: String,
    val prompt: String,
    val temperature: Double
)