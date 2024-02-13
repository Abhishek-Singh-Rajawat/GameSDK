package com.itg.itggaming.util

import com.itg.itggaming.gameLanding.api.model.GameList
import com.itg.itggaming.gameLanding.api.model.Games

object GameConstants {

    const val AD_BUNDLE="adsData"
    const val AD_UNIT_ID="adUnitId"
    const val AD_CATEGORY_POSITION="adCategoryPosition"

    const val CATEGORY_DATA="categoryData"
    const val GAME_DATA="gameData"

    const val EMPTY=""
    const val LANGUAGE="language"

    const val BASE_URL="baseURL"
    const val SHARED_ID="sharedid"

    val EMPTY_AD_DATA= GameList(type = "ads","","", arrayListOf())
    val EMPTY_GAME_DATA=Games("","","","")


}