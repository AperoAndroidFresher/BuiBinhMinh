package com.example.buibinhminh.retrofit

import com.example.buibinhminh.data.SongDto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/techtrek/Remote_audio.json")
    fun getSongs(): Call<List<SongDto>>
}