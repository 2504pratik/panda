package com.example.panda.models.chat

data class Usage(
    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)