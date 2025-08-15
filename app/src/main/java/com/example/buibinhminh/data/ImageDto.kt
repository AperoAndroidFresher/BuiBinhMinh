package com.example.buibinhminh.data

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("#text")
    val url: String,
    @SerializedName("size")
    val size: String
)