package com.example.materialyou.model.dto


import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseData(
    val copyright: String,
    val date: String,
    val explanation: String,
    @SerializedName("hdurl")
    val hdUrl: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String
)