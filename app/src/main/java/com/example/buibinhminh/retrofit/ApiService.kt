package com.example.buibinhminh.retrofit

import com.example.buibinhminh.data.SongDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("/techtrek/Remote_audio.json")
    suspend fun getSongs(): Response<List<SongDto>>

    @GET
    suspend fun downloadSong(@Url url: String): ResponseBody
}