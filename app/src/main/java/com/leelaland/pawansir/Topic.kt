package com.leelaland.pawansir

import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("class") val className: String,
    val topic_name: String,
    val description: String,
    val ai_prompt: String
)
