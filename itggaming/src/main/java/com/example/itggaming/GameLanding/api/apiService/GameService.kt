package com.example.itggaming.GameLanding.api.apiService

import com.example.itggaming.GameLanding.api.model.GameLandingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GameService {
    @GET
    suspend fun getGames(@Url url:String):Response<GameLandingModel>
}