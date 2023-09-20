package com.example.itggaming.GameLanding.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.itggaming.GameLanding.api.repository.GameLandingRepository

class MainViewModelFactory(private val repository: GameLandingRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}