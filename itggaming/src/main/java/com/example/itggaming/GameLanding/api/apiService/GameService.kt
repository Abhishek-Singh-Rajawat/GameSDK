package com.example.itggaming.GameLanding.api.apiService

import com.example.itggaming.GameLanding.api.model.GameLandingModel
import retrofit2.Response
import retrofit2.http.GET

interface GameService {
    @GET("includeEverything.json")
    suspend fun getGames():Response<GameLandingModel>
}