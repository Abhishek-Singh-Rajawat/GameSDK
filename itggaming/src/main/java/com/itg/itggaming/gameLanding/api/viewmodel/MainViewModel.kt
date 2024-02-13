package com.itg.itggaming.gameLanding.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itg.itggaming.gameLanding.api.model.GameLandingModel
import com.itg.itggaming.gameLanding.api.repository.GameLandingRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class MainViewModel(private val repository: GameLandingRepository, url:String):ViewModel() {

    val message = MutableLiveData<String>()
    init {
        viewModelScope.launch (Dispatchers.IO){
            try {
                repository.getGames(url)
                message.postValue("Success")
            }
            catch (err:Exception){
                message.postValue("Failure")
                err.printStackTrace()
            }
        }

    }

    val games:LiveData<GameLandingModel>
         get() = repository.games

}