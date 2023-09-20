package com.example.itggaming.GameLanding.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itggaming.GameLanding.api.model.GameLandingModel
import com.example.itggaming.GameLanding.api.repository.GameLandingRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class MainViewModel(private val repository: GameLandingRepository,url:String):ViewModel() {

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository.getGames(url)
        }

    }
     val games:LiveData<GameLandingModel>
         get() = repository.games

}