package com.example.itggaming.GameLanding.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.itggaming.GameLanding.api.apiService.GameService
import com.example.itggaming.GameLanding.api.model.GameLandingModel

class GameLandingRepository(private val gameService: GameService) {

    private val gamesLiveData=MutableLiveData<GameLandingModel>()

    val games:LiveData<GameLandingModel>
        get() =gamesLiveData

    suspend fun getGames(url :String){
        val result=gameService.getGames(url)
        if(result?.body() != null){
            gamesLiveData.postValue(result.body())
        }
    }
}