package com.itg.itggaming.gameLanding.api.apiService

import com.itg.itggaming.gameLanding.api.model.GameLandingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GameService {
    @GET
    suspend fun getGames(@Url url:String):Response<GameLandingModel>
}