package com.example.itggaming.GameLanding.api.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker.Result.Success
import com.example.itggaming.GameLanding.api.model.GameLandingModel
import com.example.itggaming.GameLanding.api.repository.GameLandingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.handleCoroutineException

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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