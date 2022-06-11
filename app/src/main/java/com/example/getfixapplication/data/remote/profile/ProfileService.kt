package com.example.getfixapplication.data.remote.profile

import com.example.getfixapplication.data.model.Teknisi
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("teknisi/{id}")
    suspend fun getTeknisiData(
        @Path("id") username: String
    ): Teknisi

    @GET("teknisi/{layanan}/{area}")
    suspend fun getTeknisiList(
        @Path("layanan") layanan: String,
        @Path("area") area: String
    ): List<Teknisi>
}