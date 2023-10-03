package com.example.itggaming.GameLanding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itggaming.GameLanding.api.adapter.GameLandingAdapter
import com.example.itggaming.GameLanding.api.apiService.GameService
import com.example.itggaming.GameLanding.api.apiService.RetrofitHelper
import com.example.itggaming.GameLanding.api.model.Data
import com.example.itggaming.GameLanding.api.model.GameList
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.GameLanding.api.repository.GameLandingRepository
import com.example.itggaming.GameLanding.api.viewmodel.MainViewModel
import com.example.itggaming.GameLanding.api.viewmodel.MainViewModelFactory
import com.example.itggaming.R
import com.example.itggaming.util.GameConstants
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import java.util.Locale

class GameLandingActivity : AppCompatActivity() {

    private lateinit var data:Data
    private lateinit var dataList:ArrayList<GameList>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var url: String
    private lateinit var repository: GameLandingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        setContentView(R.layout.activity_game_landing)
        initializeModels()
        getGameList()
        setSystemProperties()
        setBackBtn()
    }

    private fun setLanguage() {
        val language=intent.getStringExtra(GameConstants.LANGUAGE)
        if (language != null) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                val config = this.resources.configuration
                config.setLayoutDirection(locale)
                config.setLocale(locale)
                resources.updateConfiguration(config, resources.displayMetrics)
        }
    }

    private fun initializeModels() {
        url=intent.getStringExtra(GameConstants.BASE_URL)!!
        val gameService=RetrofitHelper.getInstance().create(GameService::class.java)
        repository=GameLandingRepository(gameService)
        callApi(repository,url)
    }

    private fun callApi(repository: GameLandingRepository, url: String?) {
        if(url!=null)
            mainViewModel=ViewModelProvider(this,MainViewModelFactory(repository,url)).get(MainViewModel::class.java)
    }


    private fun setBackBtn() {
        val backBtn=findViewById<ImageView>(R.id.iv_back_btn)
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView() {
        val adsData= data.ads
        val adBundle=Bundle()
        if (adsData != null) {
            adBundle.putString(GameConstants.AD_UNIT_ID,adsData.adUnitId)
            adsData.adCategoryPosition?.let { adBundle.putInt(GameConstants.AD_CATEGORY_POSITION, it) }
        }
        val adapter=GameLandingAdapter(adBundle,dataList)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_gameLanding)
        recyclerView.visibility=View.VISIBLE
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter=adapter
    }

    private fun setDataWithAds() {
        val gameDataListwithAds= addAdsToList()
        dataList= arrayListOf()
        dataList.clear()
        dataList.addAll(gameDataListwithAds)
    }

    private fun setSystemProperties() {
        window.statusBarColor=ContextCompat.getColor(this,R.color.toolbar)
        val windowInsetsController=WindowCompat.getInsetsController(window,window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        windowInsetsController.systemBarsBehavior= WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun addAdsToList(): ArrayList<GameList> {
        val adPosition= data.ads?.adLandingPosition?.plus(1)
        var i= data.ads?.adLandingPosition
        val arr=dataList
        val adsData=GameList(type = "ads","","", arrayListOf())
        if (i != null && i!=0) {
                while(i<arr.size){
                    arr.add(i,adsData)
                    if (adPosition != null) {
                        i+=adPosition
                    }
                }
        }
        return arr
    }

    private fun getGameList(){
        mainViewModel.games.observe(this, Observer {
            if(it.data!=null){
                data= it.data!!
                dataList=data.gameList
                val shimmer=findViewById<ShimmerFrameLayout>(R.id.shimmer)
                shimmer.stopShimmer()
                shimmer.visibility= View.GONE
                setFilterData()
                setDataWithAds()
                setRecyclerView()
            }
        })
    }

    private fun setFilterData() {
        val gameData=filterDataList()
        dataList.clear()
        dataList.addAll(gameData)
    }

    private fun filterDataList():ArrayList<GameList> {
        val arr= arrayListOf<GameList>()
        arr.addAll(dataList)
        var i=0;
        while (i<arr.size){
            var filterListDevices=arr[i].games.filter{ !(it.device.equals("all") || it.device.equals("android"))} as ArrayList<Games>
//            arr[i].games.removeIf { !(it.device.equals("all") || it.device.equals("android")) }
            val filterListEnabled=arr[i].games.filter {it.enabled!!.equals("false") }
//            arr[i].games.removeIf { it.enabled.equals("false") }
            arr[i].games.removeAll(filterListDevices)
            arr[i].games.removeAll(filterListEnabled)
            if(arr[i].games.size==0)
                arr.removeAt(i)
            i++

        }

        return arr
    }

}