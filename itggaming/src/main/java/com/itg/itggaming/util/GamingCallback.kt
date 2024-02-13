package com.itg.itggaming.util

object GamingCallback {
    var logCallbacks: GamingLogCallbacks? =null

    fun onCategorySelected(name:String){
        logCallbacks?.onCategoryClicked(name)
    }
    fun onGameSelected(name:String){
        logCallbacks?.onGameClicked(name)
    }
}