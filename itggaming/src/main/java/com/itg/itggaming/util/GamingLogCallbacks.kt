package com.itg.itggaming.util

import java.io.Serializable

interface GamingLogCallbacks:Serializable {
    fun onCategoryClicked(name: String)
    fun onGameClicked(name: String)
}