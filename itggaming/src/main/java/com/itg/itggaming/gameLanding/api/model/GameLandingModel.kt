package com.itg.itggaming.gameLanding.api.model

import com.itg.itggaming.util.GameConstants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GameLandingModel(
    @SerializedName("data" ) var data : Data? = Data()

)

data class Games (

    @SerializedName("id"            ) var id          : String? =  GameConstants.EMPTY,
    @SerializedName("game_name"     ) var gameName    : String? =  GameConstants.EMPTY,
    @SerializedName("game_play_url" ) var gamePlayUrl : String? =  GameConstants.EMPTY,
    @SerializedName("type"          ) var type        : String? =  GameConstants.EMPTY,
    @SerializedName("orientation"   ) var orientation : String? =  GameConstants.EMPTY,
    @SerializedName("images"        ) var images      : Images? = Images(),
    @SerializedName("description"   ) var description : String? =  GameConstants.EMPTY,
    @SerializedName("enabled"       ) var enabled     : String? =  GameConstants.EMPTY,
    @SerializedName("device"        ) var device      : String? =  GameConstants.EMPTY,
    @SerializedName("fullscreen"    ) var isFullscreen: String? =  GameConstants.EMPTY

):Serializable

data class Images (

    @SerializedName("icon"             ) var icon             : String? =  GameConstants.EMPTY,
    @SerializedName("horizontalBanner" ) var horizontalBanner : String? =  GameConstants.EMPTY,
    @SerializedName("verticalBanner"   ) var verticalBanner   : String? =  GameConstants.EMPTY

):Serializable

data class GameList (

    @SerializedName("type"       ) var type       : String?          = GameConstants.EMPTY,
    @SerializedName("category"   ) var category   : String?          =  GameConstants.EMPTY,
    @SerializedName("categoryid" ) var categoryid : String?          =  GameConstants.EMPTY,
    @SerializedName("games"      ) var games      : ArrayList<Games> = arrayListOf()

) :Serializable{}

data class Ads (

    @SerializedName("ad_unit_id"           ) var adUnitId           : String? =  GameConstants.EMPTY,
    @SerializedName("ad_landing_position"  ) var adLandingPosition  : Int?    =  null,
    @SerializedName("ad_category_position" ) var adCategoryPosition : Int?    = null

)

data class Data (

    @SerializedName("ads"       ) var ads      : Ads?                = Ads(),
    @SerializedName("game_list" ) var gameList : ArrayList<GameList> = arrayListOf()

)

