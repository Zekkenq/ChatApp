package com.example.chatapp.data.dto

import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("device_id")
    val deviceId: String,
    val message: String
)