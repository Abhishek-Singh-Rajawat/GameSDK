package com.example.itggaming.GameCategories

import GameCategoriesAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.R
import com.example.itggaming.util.GameConstants
import java.io.Serializable
import kotlin.properties.Delegates

private lateinit var categoryData:GameList

class GameCategoryActivity : AppCompatActivity() {
    private lateinit var  adsData:Bundle
    private var adItemPosition by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_category)

        setSystemProperties()
        setBackBtn()
        setIntentData()
        initViews()
    }

    private fun setIntentData() {
        adsData= intent.getBundleExtra(GameConstants.AD_BUNDLE)!!
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            setData(intent.getSerializableExtra(GameConstants.CATEGORY_DATA,GameList::class.java))
        }
        else{
            setData(intent.getSerializableExtra(GameConstants.CATEGORY_DATA))
        }
    }

    private fun setSystemProperties() {
        window.statusBarColor=ContextCompat.getColor(this,R.color.itgg_toolbar)
        window.navigationBarColor=ContextCompat.getColor(this,R.color.itgg_black)
    }

    private fun initViews() {
        val categoryHeading=findViewById<TextView>(R.id.toolbar_category_name)
        categoryHeading.text= categoryData.category
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerView=findViewById<RecyclerView>(R.id.rv_gameCategory)

        val adsRowPosition=adsData.getInt(GameConstants.AD_CATEGORY_POSITION)
        adItemPosition=adsRowPosition*2+1

        val gameData= categoryData.games

        val gameDataWithAds=if(adsRowPosition!=0){
            addAdsToList(gameData)
        }
        else
            gameData

        val adapter= adsData.getString(GameConstants.AD_UNIT_ID)
            ?.let { GameCategoriesAdapter(it, gameDataWithAds,recyclerView) }
        recyclerView.adapter=adapter
        val gridLayoutManager=GridLayoutManager(this,2)

        gridLayoutManager.spanSizeLookup=object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adsCondition=gameDataWithAds.get(position).type=="ads"
                if(adsCondition)
                    return 2
                else
                    return 1
            }
        }

        recyclerView.layoutManager=gridLayoutManager
    }

    private fun setData(serializableExtra: Serializable?) {
        categoryData = serializableExtra as GameList
    }

    private fun addAdsToList(arr:ArrayList<Games>): ArrayList<Games> {
        var i=adItemPosition-1
        val adsData=Games("","","","ads","")
            while(i<arr.size){
                arr.add(i,adsData)
                i+=adItemPosition
            }
        return arr
    }

    private fun setBackBtn() {
        val back_btn=findViewById<ImageView>(R.id.iv_cat_back_btn)
        back_btn.setOnClickListener {
            finish()
        }
    }
}