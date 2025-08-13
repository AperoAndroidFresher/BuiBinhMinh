package com.example.buibinhminh.data

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("mbid")
    val mbid: String?, 
    @SerializedName("url")
    val url: String?, 
    @SerializedName("image")
    val images: List<ImageDto>?
)